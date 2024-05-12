package ouachousoft.BackEnd0.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ouachousoft.BackEnd0.dto.AuthentificationDTO;
import ouachousoft.BackEnd0.entity.Utilisateur;
import ouachousoft.BackEnd0.securite.JwtService;
import ouachousoft.BackEnd0.service.UtilisateurService;

import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class UtilisateurControleur {

    private AuthenticationManager authenticationManager;
    private UtilisateurService utilisateurService;
    private JwtService jwtService;

    @PostMapping(path = "inscription")
    public ResponseEntity<String> inscription(@RequestBody Utilisateur utilisateur){
        try {
            utilisateurService.inscription(utilisateur);
            return ResponseEntity.ok("Inscription successful");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }



    @PostMapping(path = "activation")
    public ResponseEntity<String> activation(@RequestBody Map<String, String> activation) {
        try {
            utilisateurService.activation(activation);
            return ResponseEntity.ok("Activation successful");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "deconnexion")
    public void deconnexion(){
        this.jwtService.deconnexion();
    }






    @PostMapping(path = "connexion")
    public ResponseEntity<Map<String, String>> connexion(@RequestBody AuthentificationDTO authentificationDTO) {
        try {
            final Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authentificationDTO.username(), authentificationDTO.password())
            );

            if (authenticate.isAuthenticated()) {
                Map<String, String> response = this.jwtService.generate(authentificationDTO.username());
                response = new HashMap<>(response); // Create a mutable copy
                response.put("message", "Connecté");
                return ResponseEntity.ok(response);
            } else {
                // Authentication failed
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "Email ou mot de passe incorrect"));
            }
        } catch (UsernameNotFoundException e) {
            // Username not found (email does not exist)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "Email non trouvé"));
        }
    }
}
