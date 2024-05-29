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
            String resetPasswordUrl = "http://localhost:4200/password-reset";

            String text = String.format(
                    "<html>" +
                            "<body style=\"font-family: Arial, sans-serif; line-height: 1.6; background-color: #f4f4f4; padding: 20px;\">" +
                            "<div style=\"max-width: 600px; margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px; background-color: #ffffff; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\">" +
                            "<div style=\"text-align: center; margin-bottom: 20px;\">" +
                            "<img src=\"https://example.com/logo.png\" alt=\"Logo\" style=\"width: 100px;\">" +
                            "</div>" +
                            "<h2 style=\"color: #333; text-align: center;\">Bonjour %s,</h2>" +
                            "<p style=\"font-size: 16px; color: #555;\">Nous sommes ravis de vous accueillir sur l'application. Pour commencer, veuillez utiliser le code activation suivant pour vous connecter :</p>" +
                            "<h2 style=\"color: #007bff; text-align: center;\">%s</h2>" +
                            "<div style=\"text-align: center; margin: 20px 0;\">" +
                            "<a href=\"%s\" style=\"display: inline-block; padding: 15px 25px; font-size: 16px; color: #ffffff; background-color: #28a745; border-radius: 5px; text-decoration: none;\">Accéder à l'application</a>" +
                            "</div>" +
                            "<p style=\"font-size: 16px; color: #555; text-align: center;\">Cordialement,</p>" +
                            "<p style=\"font-size: 16px; color: #555; text-align: center;\">_____________</p>" +
                            "<p style=\"font-size: 16px; color: #555; text-align: center;\">________________________________</p>" +
                            "</div>" +
                            "</body>" +
                            "</html>",
                    validation.getUtilisateur().getNom(),
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
