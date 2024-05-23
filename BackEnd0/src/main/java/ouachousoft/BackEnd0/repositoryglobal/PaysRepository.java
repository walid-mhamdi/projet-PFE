package ouachousoft.BackEnd0.repositoryglobal;

import ouachousoft.BackEnd0.entity.Pays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaysRepository extends JpaRepository<Pays, Integer> {
    Pays findByCode(String code);

    Pays findBylibelle(String libelle);
}
