package ouachousoft.BackEnd0.repositoryglobal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ouachousoft.BackEnd0.entity.Banque;

@Repository
public interface BanqueRepository extends JpaRepository<Banque, Integer> {
    Banque findByCode(String code);
    Banque findByLibelle(String libelle);
}
