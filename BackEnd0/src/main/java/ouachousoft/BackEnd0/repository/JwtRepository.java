package ouachousoft.BackEnd0.repository;

import org.springframework.data.repository.CrudRepository;
import ouachousoft.BackEnd0.entity.Jwt;

import java.util.Optional;

public interface JwtRepository extends CrudRepository<Jwt, Integer> {
    Optional<Jwt> findByValue(String valeur);
}
