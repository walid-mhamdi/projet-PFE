package ouachousoft.BackEnd0.service;

import lombok.AllArgsConstructor;

import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import ouachousoft.BackEnd0.TypeDeRole;
import ouachousoft.BackEnd0.entity.Role;
import ouachousoft.BackEnd0.entity.Utilisateur;
import ouachousoft.BackEnd0.entity.Validation;
import ouachousoft.BackEnd0.repository.UtilisateurRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@AllArgsConstructor
@Service
@CrossOrigin(origins = "http://localhost:4200",methods ={ RequestMethod.GET,RequestMethod.DELETE,RequestMethod.POST,RequestMethod.PUT})  // Ajout de cette ligne pour permettre les requêtes CORS depuis localhost:4200

public class UtilisateurService implements UserDetailsService {

    private UtilisateurRepository utilisateurRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private ValidationService validationService;

    public void inscription(Utilisateur utilisateur) {
        // Vérifier la validité de l'adresse email
        if (!utilisateur.getEmail().contains("@") || !utilisateur.getEmail().contains(".")) {
            throw new RuntimeException("Adresse email invalide");
        }

        // Vérifier si l'adresse email est déjà utilisée
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findByEmail(utilisateur.getEmail());
        if (utilisateurOptional.isPresent()) {
            throw new RuntimeException("Adresse email déjà utilisée");
        }

        // Crypter le mot de passe
        String mdpCrypte = passwordEncoder.encode(utilisateur.getMdp());
        utilisateur.setMdp(mdpCrypte);

        // Définir le rôle de l'utilisateur
        Role roleUtilisateur = new Role();
        roleUtilisateur.setLibelle(TypeDeRole.UTILISATEUR);
        utilisateur.setRole(roleUtilisateur);

        // Enregistrer l'utilisateur dans la base de données
        utilisateur = utilisateurRepository.save(utilisateur);

        // Enregistrer une validation pour l'utilisateur
        validationService.enregistrer(utilisateur);
    }

    public void activation(Map<String, String> activation) {
        // Lire la validation en fonction du code
        Validation validation = validationService.lireEnFonctionDuCode(activation.get("code"));

        // Vérifier si le code de validation est expiré
        if (Instant.now().isAfter(validation.getExpiration())) {
            throw new RuntimeException("Code de validation expiré");
        }
        // Vérifier si le code est déjà utilisé
        if (validation.getCode() == null) {
            throw new RuntimeException("Code de validation déjà utilisé");
        }
        // Activer l'utilisateur
        Utilisateur utilisateurActive = utilisateurRepository.findById(validation.getUtilisateur().getId())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        utilisateurActive.setActif(true);

        this.utilisateurRepository.save(utilisateurActive); // Sauvegarder l'état actif de l'utilisateur

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


    public void modifieMotDePasse(Map<String, String> parameters) {

        Utilisateur utilisateur = this.loadUserByUsername(parameters.get("email"));
        // Trouver tous les enregistrements de validation associés à cet utilisateur
        List<Validation> validations = validationService.findAllByUtilisateur(utilisateur);

        // Supprimer tous les enregistrements de validation
        for (Validation validation : validations) {
            validationService.supprimer(validation);
        }
        validationService.enregistrer(utilisateur);
    }

      public void nouveauMotDePasse(Map<String, String> parameters) {
        Utilisateur utilisateur = this.loadUserByUsername(parameters.get("email"));
        final Validation validation = validationService.lireEnFonctionDuCode(parameters.get("code"));
        if (validation.getUtilisateur().getEmail().equals(utilisateur.getEmail()))
        {
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

            //utilisateurRepository.delete(utilisateur.get());
        } else {
            throw new RuntimeException("Utilisateur introuvable");
        }
    }

}
