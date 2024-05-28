package ouachousoft.BackEnd0.repositoryglobal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ouachousoft.BackEnd0.entity.Qualite;

@Repository
public interface QualiteRepository extends JpaRepository<Qualite, Integer> {
    Qualite findByCode(String code);
    Qualite findByLibelle(String libelle);
}
