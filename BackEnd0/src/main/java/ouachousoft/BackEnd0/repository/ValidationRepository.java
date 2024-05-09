package ouachousoft.BackEnd0.repository;

import org.springframework.data.repository.CrudRepository;
import ouachousoft.BackEnd0.entity.Validation;

import java.util.Optional;

public interface ValidationRepository extends CrudRepository<Validation, Integer> {

    Optional<Validation> findByCode(String code);
}
