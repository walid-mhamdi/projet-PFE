package ouachousoft.BackEnd0.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ouachousoft.BackEnd0.TypeDeRole;
import ouachousoft.BackEnd0.dto.InscriptionDTO;
import ouachousoft.BackEnd0.entity.Role;
import ouachousoft.BackEnd0.entity.Utilisateur;
import ouachousoft.BackEnd0.entity.Validation;
import ouachousoft.BackEnd0.repository.RoleRepository;
import ouachousoft.BackEnd0.repository.UtilisateurRepository;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UtilisateurService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ValidationService validationService;

    public void inscription(InscriptionDTO inscriptionDTO) {
        // Vérifier la validité de l'adresse email
        if (!inscriptionDTO.getEmail().contains("@") || !inscriptionDTO.getEmail().contains(".")) {
            throw new RuntimeException("Adresse email invalide");
        }

        // Vérifier si l'adresse email est déjà utilisée
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findByEmail(inscriptionDTO.getEmail());
        if (utilisateurOptional.isPresent()) {
            throw new RuntimeException("Adresse email déjà utilisée");
        }

        // Vérifier si le nom est fourni
        if (inscriptionDTO.getNom() == null || inscriptionDTO.getNom().isEmpty()) {
            throw new RuntimeException("Le nom est obligatoire");
        }

        // Crypter le mot de passe
        String mdpCrypte = passwordEncoder.encode(inscriptionDTO.getMdp());

        // Créer un nouvel utilisateur
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(inscriptionDTO.getNom());
        utilisateur.setEmail(inscriptionDTO.getEmail());
        utilisateur.setMdp(mdpCrypte);

        // Définir le rôle de l'utilisateur
        Role roleUtilisateur = roleRepository.findById(inscriptionDTO.getRole())
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setLibelle(inscriptionDTO.getRole());
                    return roleRepository.save(newRole);
                });

        utilisateur.setRole(roleUtilisateur);

        // Enregistrer l'utilisateur dans la base de données
        utilisateur = utilisateurRepository.save(utilisateur);

        // Enregistrer une validation pour l'utilisateur
        validationService.enregistrer(utilisateur);
    }


    @Override
    public Utilisateur loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findByEmail(username);
        if (utilisateurOptional.isPresent()) {
            return utilisateurOptional.get();
        } else {
            throw new UsernameNotFoundException("Utilisateur non trouvé avec l'email: " + username);
        }
    }

    public void activation(Map<String, String> activation) {
        Validation validation = validationService.lireEnFonctionDuCode(activation.get("code"));
        if (Instant.now().isAfter(validation.getExpiration())) {
            throw new RuntimeException("Code de validation expiré");
        }
        if (validation.getCode() == null) {
            throw new RuntimeException("Code de validation déjà utilisé");
        }
        Utilisateur utilisateurActive = utilisateurRepository.findById(validation.getUtilisateur().getId())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        utilisateurActive.setActif(true);
        this.utilisateurRepository.save(utilisateurActive);
    }

    public void modifieMotDePasse(Map<String, String> parameters) {
        Utilisateur utilisateur = this.loadUserByUsername(parameters.get("email"));
        List<Validation> validations = validationService.findAllByUtilisateur(utilisateur);
        for (Validation validation : validations) {
            validationService.supprimer(validation);
        }
        validationService.enregistrer(utilisateur);
    }

    public void nouveauMotDePasse(Map<String, String> parameters) {
        Utilisateur utilisateur = this.loadUserByUsername(parameters.get("email"));
        Validation validation = validationService.lireEnFonctionDuCode(parameters.get("code"));
        if (validation.getUtilisateur().getEmail().equals(utilisateur.getEmail())) {
            String mdpCrypte = this.passwordEncoder.encode(parameters.get("password"));
            utilisateur.setMdp(mdpCrypte);
            this.utilisateurRepository.save(utilisateur);
        }
    }

    public void supprimerUtilisateur(int id) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);
        if (utilisateur.isPresent()) {
            Utilisateur user = utilisateur.get();
            utilisateurRepository.delete(user);
        } else {
            throw new RuntimeException("Utilisateur introuvable");
        }
    }
}
