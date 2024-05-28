package ouachousoft.BackEnd0.controllerglobal;

import ouachousoft.BackEnd0.entity.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ouachousoft.BackEnd0.serviceglobal.OperationService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/operations")
@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT})
public class OperationController {

    @Autowired
    private OperationService operationService;

    @PostMapping
    public ResponseEntity<?> createOperation(@RequestBody Operation operation) {
        if (operation.getMontant() != null && operation.getTypeOperation() != null) {
            // Initialisez la date de valeur au moment actuel
            operation.setDateValeur(LocalDateTime.now());
            Operation savedOperation = operationService.saveOperation(operation);
            return ResponseEntity.ok(savedOperation);
        } else {
            return ResponseEntity.badRequest().body("Le montant et le type d'opération doivent être fournis.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Operation>> getAllOperations() {
        List<Operation> operationList = operationService.getAllOperations();
        return ResponseEntity.ok(operationList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Operation> getOperationById(@PathVariable int id) {
        Operation operation = operationService.getOperationById(id);
        if (operation != null) {
            return ResponseEntity.ok(operation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
