package ouachousoft.BackEnd0.repository;

import org.springframework.data.repository.CrudRepository;
import ouachousoft.BackEnd0.entity.Utilisateur;

import java.util.Optional;

public interface UtilisateurRepository extends CrudRepository<Utilisateur, Integer> {
    Optional<Utilisateur> findByEmail(String email);
}
