package ouachousoft.BackEnd0.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ouachousoft.BackEnd0.entity.Utilisateur;
import ouachousoft.BackEnd0.entity.Validation;
import ouachousoft.BackEnd0.repository.ValidationRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
@Service
public class ValidationService {

    private ValidationRepository validationRepository;
    private NotificationService notificationService;

    public void enregistrer(Utilisateur utilisateur) {

        Validation validation = new Validation();
        validation.setUtilisateur(utilisateur);

        Instant creation = Instant.now();
        validation.setCreation(creation);

        Instant expiration = creation.plus(10, ChronoUnit.MINUTES);
        validation.setExpiration(expiration);

        Random random = new Random();
        int randomInteger = random.nextInt(999999);
        String code = String.format("%06d", randomInteger);

        validation.setCode(code);
        // Set the email here (you can retrieve this from the utilisateur or pass it as a parameter)
        validation.setEmail(utilisateur.getEmail()); // Assuming Utilisateur has an getEmail() method

        this.validationRepository.save(validation);
        this.notificationService.envoyer(validation);
    }

    public Validation lireEnFonctionDuCode(String code) {
        return this.validationRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Code de validation invalide"));
    }

    // Supprimer l'enregistrement de validation de la base de données
    public void supprimer(Validation validation) {
        validationRepository.delete(validation);
    }


    public List<Validation> findAllByUtilisateur(Utilisateur utilisateur) {
        return validationRepository.findAllByUtilisateur(utilisateur);
    }
}
