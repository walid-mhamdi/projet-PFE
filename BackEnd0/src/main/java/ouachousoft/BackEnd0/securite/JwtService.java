package ouachousoft.BackEnd0.securite;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ouachousoft.BackEnd0.entity.Utilisateur;
import ouachousoft.BackEnd0.service.UtilisateurService;

import java.security.Key;
import java.util.Date;
import java.util.Map;
@AllArgsConstructor
@Service
public class JwtService {
    private final String ENCRIPTION_KEY="044ec15fad591b1225b461ecb9baeba6377ec3af3ca191a61c15f5b2b8c47bab";
    private UtilisateurService utilisateurService;
    public Map<String, String> generate(String username){
        Utilisateur utilisateur = (Utilisateur) this.utilisateurService.loadUserByUsername(username);

        return this.generateJwt(utilisateur);

    }

    private Map<String, String> generateJwt(Utilisateur utilisateur) {
        final Map<String, String> claims = Map.of("nom", utilisateur.getNom(),
                "email", utilisateur.getEmail());

        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime +30*60*1000;
        final String bearer = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(utilisateur.getEmail())
                .setClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();


        return Map.of("bearer",bearer);
    }

    private Key getKey() {
        final byte[] decoder = Decoders.BASE64.decode(ENCRIPTION_KEY);
        return Keys.hmacShaKeyFor(decoder);
    }


}
