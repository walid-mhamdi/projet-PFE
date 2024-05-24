package ouachousoft.BackEnd0.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ouachousoft.BackEnd0.entity.Validation;

@Service
public class NotificationService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public NotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void envoyer(Validation validation) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("walidmhamdi92@gmail.com");
            helper.setTo(validation.getUtilisateur().getEmail());
            helper.setSubject("Votre code d'activation");

            // Construct the URL for resetting the password
            String resetPasswordUrl = "http://localhost:4200/password-reset" ;

            String text = String.format(
                    "<html>" +
                            "<body>" +
                            "<p>Bonjour %s,</p>" +
                            "<p>Nous sommes ravis de vous accueillir dans notre application. Voici vos informations de connexion :</p>" +
                            "<p><strong>Email:</strong> %s</p>" +
                            "<p><strong>Voici également votre code d'activation :</strong></p>" +
                            "<h2>%s</h2>" +
                            "<p>Cliquez sur le bouton ci-dessous pour définir un nouveau mot de passe :</p>" +
                            "<a href=\"%s\" style=\"display: inline-block; padding: 10px 20px; font-size: 16px; color: #ffffff; background-color: #007bff; border-radius: 5px; text-decoration: none;\">Définir un nouveau mot de passe</a>" +
                            "<p>Cordialement,</p>" +
                            "<p>L'équipe de votre application</p>" +
                            "</body>" +
                            "</html>",
                    validation.getUtilisateur().getNom(),
                    validation.getUtilisateur().getEmail(),
                    validation.getCode(),
                    resetPasswordUrl
            );

            helper.setText(text, true); // Set to true to indicate HTML content

            javaMailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
            // Gérer l'exception de manière appropriée
        }
    }
}
