package ouachousoft.BackEnd0.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
        SimpleMailMessage Message = new SimpleMailMessage();
        Message.setFrom("walidmhamdi92@gmail.com");
        Message.setTo(validation.getUtilisateur().getEmail());
        Message.setSubject("Votre code d'activation");

        String text = String.format("Bonjour %s, voici votre code d'activation : %s",
                validation.getUtilisateur().getNom(),
                validation.getCode());
        Message.setText(text);

        javaMailSender.send(Message);
    }
}
