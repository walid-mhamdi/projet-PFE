package ouachousoft.BackEnd0.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ouachousoft.BackEnd0.dto.AuthentificationDTO;
import ouachousoft.BackEnd0.dto.InscriptionDTO;
import ouachousoft.BackEnd0.dto.UtilisateurDTO;
import ouachousoft.BackEnd0.entity.Utilisateur;
import ouachousoft.BackEnd0.repository.UtilisateurRepository;
import ouachousoft.BackEnd0.securite.JwtService;
import ouachousoft.BackEnd0.service.UtilisateurService;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
//@AllArgsConstructor
@RequiredArgsConstructor
@RestController
@RequestMapping()
@CrossOrigin(origins = "http://localhost:4200",methods ={ RequestMethod.GET,RequestMethod.DELETE,RequestMethod.POST,RequestMethod.PUT})

public class UtilisateurControleur {

    private final AuthenticationManager authenticationManager;
    private final UtilisateurService utilisateurService;
    private final JwtService jwtService;
    private final UtilisateurRepository utilisateurRepository;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/utilisateurs")
    public ResponseEntity<List<UtilisateurDTO>> getUtilisateurs() {
        List<Utilisateur> utilisateurs = utilisateurService.getAllUtilisateurs();
        List<UtilisateurDTO> utilisateurDTOs = utilisateurs.stream()
                .map(utilisateur -> new UtilisateurDTO(
                        utilisateur.getId(),
                        utilisateur.getNom(),
                        utilisateur.getEmail(),
                        utilisateur.getRole().getLibelle(),
                        utilisateur.isActif()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(utilisateurDTOs);
    }
    @PostMapping(path = "/inscription")
    public ResponseEntity<String> inscription(@RequestBody InscriptionDTO inscriptionDTO) {
        try {
            utilisateurService.inscription(inscriptionDTO);
            return ResponseEntity.ok("Inscription réussie");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @PostMapping(path = "modifie-mot-de-passe")
    public ResponseEntity<Map<String, String>> modifieMotDePasse(@RequestBody Map<String, String> activation) {
        try {
            utilisateurService.modifieMotDePasse(activation);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Un email de réinitialisation de mot de passe a été envoyé.");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }



    @PostMapping(path = "nouveau-mot-de-passe")
    public void nouveauMotDePasse(@RequestBody Map<String, String> activation) {
        this.utilisateurService.nouveauMotDePasse(activation);
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


    @PostMapping(path = "connexion")
    public ResponseEntity<Map<String, String>> connexion(@RequestBody AuthentificationDTO authentificationDTO) {
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findByEmail(authentificationDTO.email());

        if (utilisateurOptional.isEmpty()) {
            // Email does not exist
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "Email incorrect"));
        }

        try {
            final Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authentificationDTO.email(), authentificationDTO.password()));

            if (authenticate.isAuthenticated()) {
                Map<String, String> response = this.jwtService.generate(authentificationDTO.email());
                response = new HashMap<>(response); // Create a mutable copy
                response.put("message", "Connecté");
                return ResponseEntity.ok(response);
            } else {
                // Authentication failed due to other reasons
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "Erreur d'authentification"));
            }
        } catch (BadCredentialsException e) {
            // Password is incorrect
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "Mot de passe incorrect"));
        }
    }


            @PostMapping(path = "deconnexion")
    public void deconnexion() {
        this.jwtService.deconnexion();
    }



    @DeleteMapping(path = "delete/{id}")
    public ResponseEntity<String> supprimerUtilisateur(@PathVariable int id) {
        try {
            utilisateurService.supprimerUtilisateur(id);
            return ResponseEntity.ok("Utilisateur supprimé avec succès");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
