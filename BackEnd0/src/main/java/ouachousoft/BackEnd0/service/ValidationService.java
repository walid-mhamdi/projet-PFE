package ouachousoft.BackEnd0.service;

import jakarta.transaction.Transactional;
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

    @Transactional
    public void enregistrer(Utilisateur utilisateur) {
        List<Validation> existingValidations = validationRepository.findAllByUtilisateur(utilisateur);

        Validation validation;
        if (existingValidations.isEmpty()) {
            validation = new Validation();
            validation.setUtilisateur(utilisateur);
            validation.setCreation(Instant.now());
        } else {

            validation = existingValidations.get(0);
            validation.setCreation(Instant.now());
        }

        Instant expiration = validation.getCreation().plus(10, ChronoUnit.MINUTES);
        validation.setExpiration(expiration);

        Random random = new Random();
        int randomInteger = random.nextInt(999999);
        String code = String.format("%06d", randomInteger);
        validation.setCode(code);
        validation.setEmail(utilisateur.getEmail());


        this.validationRepository.save(validation);

        this.notificationService.envoyer(validation);

    }

    public Validation lireEnFonctionDuCode(String code) {
        return validationRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Code de validation invalide"));
    }


}
