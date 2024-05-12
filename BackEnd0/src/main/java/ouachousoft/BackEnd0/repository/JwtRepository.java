package ouachousoft.BackEnd0.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ouachousoft.BackEnd0.entity.Jwt;

import java.util.Optional;
import java.util.stream.Stream;

public interface JwtRepository extends CrudRepository<Jwt, Integer> {

    Optional<Jwt> findByValueAndDesactiveAndExpire(String value, boolean desactive, boolean expire);

    @Query("From Jwt j WHERE j.expire= :expire AND j.desactive= :desactive AND  j.utilisateur.email= :email")
    Optional<Jwt> findUtilisateurValidToken(String email, boolean desactive, boolean expire);



    @Query("FROM Jwt j WHERE j.utilisateur.email = :email")
    Stream<Jwt> findUtilisateur(String email);

}
