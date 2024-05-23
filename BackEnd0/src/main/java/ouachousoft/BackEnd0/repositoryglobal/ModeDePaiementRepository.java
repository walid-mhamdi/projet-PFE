package ouachousoft.BackEnd0.repositoryglobal;

import ouachousoft.BackEnd0.entity.ModeDePaiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeDePaiementRepository extends JpaRepository<ModeDePaiement, Integer> {
}
