package ouachousoft.BackEnd0.serviceglobal;

import ouachousoft.BackEnd0.entity.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ouachousoft.BackEnd0.repositoryglobal.OperationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OperationService {

    @Autowired
    private OperationRepository operationRepository;

    public Operation saveOperation(Operation operation) {
        return operationRepository.save(operation);
    }

    public List<Operation> getAllOperations() {
        return operationRepository.findAll();
    }

    public Operation getOperationById(int id) {
        Optional<Operation> optionalOperation = operationRepository.findById(id);
        return optionalOperation.orElse(null);
    }

}
