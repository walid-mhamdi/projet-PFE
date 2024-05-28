package ouachousoft.BackEnd0.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import ouachousoft.BackEnd0.entity.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository extends CrudRepository<Utilisateur, Integer> {
    Optional<Utilisateur> findByEmail(String email);
    @NotNull
    List<Utilisateur> findAll();


}
