package ouachousoft.BackEnd0.repositoryglobal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ouachousoft.BackEnd0.entity.Nationalite;

@Repository
public interface NationaliteRepository extends JpaRepository<Nationalite, Integer> {
    Nationalite findByCode(String code);
    Nationalite findByLibelle(String libelle);
}
