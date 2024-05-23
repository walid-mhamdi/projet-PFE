package ouachousoft.BackEnd0.repositoryglobal;

import ouachousoft.BackEnd0.entity.Ville;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VilleRepository extends JpaRepository<Ville, Integer> {

    Ville findByCode(String code);
    Ville findByVille(String ville);



}
