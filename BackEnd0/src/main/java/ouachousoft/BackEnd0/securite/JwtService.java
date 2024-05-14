package ouachousoft.BackEnd0.securite;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import jdk.jshell.execution.Util;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ouachousoft.BackEnd0.entity.Jwt;
import ouachousoft.BackEnd0.entity.Utilisateur;
import ouachousoft.BackEnd0.repository.JwtRepository;
import ouachousoft.BackEnd0.service.UtilisateurService;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@AllArgsConstructor
@Service
public class JwtService {
    public static final String BEARER = "bearer";
    private final String ENCRIPTION_KEY="044ec15fad591b1225b461ecb9baeba6377ec3af3ca191a61c15f5b2b8c47bab";
    private UtilisateurService utilisateurService;
    private JwtRepository jwtRepository;




    public Jwt tokenByValue(String value) {
      return this.jwtRepository.findByValueAndDesactiveAndExpire(value,false,false)
              .orElseThrow(() ->new RuntimeException("token inconnu ou inconnu "));

    }


    public Map<String, String> generate(String username){
        Utilisateur utilisateur = (Utilisateur) this.utilisateurService.loadUserByUsername(username);

        // Désactiver tous les tokens existants pour cet utilisateur
        disableTokens(utilisateur);

        final Map<String, String> jwtMap = this.generateJwt(utilisateur);

        final Jwt jwt = Jwt
                .builder()
                .value(jwtMap.get(BEARER))
                .desactive(false)
                .expire(false)
                .utilisateur(utilisateur)
                .build();

        this.jwtRepository.save(jwt);

        return jwtMap;

    }

    private void disableTokens(Utilisateur utilisateur){
        final List<Jwt> jwtList = this.jwtRepository.findUtilisateur(utilisateur.getEmail()).peek(
                jwt -> {
                    jwt.setExpire(true);
                    jwt.setDesactive(true);

                }).collect(Collectors.toList());

        this.jwtRepository.saveAll(jwtList);
    }
    public String extractUsername(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = this.getClaim(token, Claims::getExpiration);

        return expirationDate.before(new Date()) ;
    }


    private <T> T getClaim(String token, Function<Claims, T> function){
        Claims claims = getAllClaims(token);
        return function.apply(claims);

    }

    private Claims getAllClaims(String token) {
        return  Jwts.parserBuilder()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    private Map<String, String> generateJwt(Utilisateur utilisateur) {
        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime +30 * 60 * 1000;

        final Map<String, Object> claims = Map.of(
                "nom", utilisateur.getNom(),
                Claims.EXPIRATION , new Date(expirationTime),
                Claims.SUBJECT,utilisateur.getEmail());


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


    public void deconnexion() {
        // Récupérer l'utilisateur actuellement authentifié
        Utilisateur utilisateur = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Rechercher un jeton valide pour cet utilisateur
        Jwt jwt = this.jwtRepository.findUtilisateurValidToken(utilisateur.getEmail(), false, false)
                .orElseThrow(() -> new RuntimeException("Token invalide"));

        // Mettre à jour le jeton (si nécessaire)
        jwt.setExpire(true);
        jwt.setDesactive(true); // Mettre le jeton comme désactivé
        this.jwtRepository.save(jwt);
    }

    @Scheduled(cron = "0 */1 * * * *") // Exécuter tous les jours à minuit
    public void removeUselessJwt(){
        log.info("Suppression des token a {}", Instant.now());
        this.jwtRepository.deleteAllByExpireAndDesactive(true,true);

    }

}
