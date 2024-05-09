package ouachousoft.BackEnd0.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ouachousoft.BackEnd0.dto.AuthentificationDTO;
import ouachousoft.BackEnd0.entity.Utilisateur;
import ouachousoft.BackEnd0.service.UtilisateurService;

import java.awt.*;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class UtilisateurControleur {

    private AuthenticationManager authenticationManager;
    private UtilisateurService utilisateurService;

    @PostMapping(path = "inscription")
    public void inscription(@RequestBody Utilisateur utilisateur){
        log.info("Inscription");
        this.utilisateurService.inscription((utilisateur));
    }

    @PostMapping(path = "activation")
    public void inscription(@RequestBody Map<String, String> activation){

        this.utilisateurService.activation(activation);
    }
    @PostMapping(path = "connexion")
    public Map<String, String> connexion(@RequestBody AuthentificationDTO authentificationDTO){
        final Authentication authenticate= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authentificationDTO.username(),authentificationDTO.password())
        );
        log.info("resultat{}",authenticate.isAuthenticated()
        );

        return null;
    }
}
