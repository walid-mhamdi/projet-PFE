package ouachousoft.BackEnd0.repositoryglobal;

import org.springframework.data.jpa.repository.JpaRepository;
import ouachousoft.BackEnd0.entity.Operation;

public interface OperationRepository extends JpaRepository<Operation, Integer> {
}
