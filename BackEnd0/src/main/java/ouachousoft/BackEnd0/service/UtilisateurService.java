package ouachousoft.BackEnd0.service;

import lombok.AllArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ouachousoft.BackEnd0.TypeDeRole;
import ouachousoft.BackEnd0.entity.Role;
import ouachousoft.BackEnd0.entity.Utilisateur;
import ouachousoft.BackEnd0.entity.Validation;
import ouachousoft.BackEnd0.repository.UtilisateurRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;


@AllArgsConstructor
@Service
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



        // Mettre à jour le code à null
        validation.setCode(null);
        // Mettre à jour la date d'activation
        Instant activationTime = Instant.now().plus(1, ChronoUnit.HOURS);
        validation.setActivation(activationTime);
        utilisateurRepository.save(utilisateurActive); // Sauvegarder l'état actif de l'utilisateur
    }




    //  @Override
  //  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

      //  return this.utilisateurRepository.findByEmail(username).orElseThrow(() ->new UsernameNotFoundException("Utilisateur non trouvé avec l'email: " + username));
   // }
      @Override
      public Utilisateur loadUserByUsername(String username) throws UsernameNotFoundException {
          Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findByEmail(username);
          if (utilisateurOptional.isPresent()) {
              return utilisateurOptional.get();
          } else {
              throw new UsernameNotFoundException("Utilisateur non trouvé avec l'email: " + username);
          }
      }

}
