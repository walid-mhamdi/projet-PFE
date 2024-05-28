package ouachousoft.BackEnd0.controllerglobal;

import ouachousoft.BackEnd0.entity.ModeDePaiement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ouachousoft.BackEnd0.serviceglobal.ModeDePaiementService;

import java.util.List;

@RestController
@RequestMapping("/mode_de_paiement")
@CrossOrigin(origins = "http://localhost:4200",methods ={ RequestMethod.GET,RequestMethod.DELETE,RequestMethod.POST,RequestMethod.PUT})  // Ajout de cette ligne pour permettre les requêtes CORS depuis localhost:4200

public class ModeDePaiementController {

    @Autowired
    private ModeDePaiementService modeDePaiementService;

    @PostMapping
    public ResponseEntity<?> createModeDePaiement(@RequestBody ModeDePaiement modeDePaiement) {

        if (modeDePaiement.getCode() != null && modeDePaiement.getLibelle() != null) {
            ModeDePaiement savedModeDePaiement = modeDePaiementService.saveModeDePaiement(modeDePaiement);
            return ResponseEntity.ok(savedModeDePaiement);
        } else {
            return ResponseEntity.badRequest().body("Le code et le libellé doivent être fournis.");
        }
    }


    @GetMapping
    public ResponseEntity<List<ModeDePaiement>> getAllModeDePaiement() {
        List<ModeDePaiement> modeDePaiementList = modeDePaiementService.getAllModeDePaiement();
        return ResponseEntity.ok(modeDePaiementList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModeDePaiement> getModeDePaiementById(@PathVariable int id) {
        ModeDePaiement modeDePaiement = modeDePaiementService.getModeDePaiementById(id);
        if (modeDePaiement != null) {
            return ResponseEntity.ok(modeDePaiement);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteModeDePaiement(@PathVariable int id) {
        boolean isDeleted = modeDePaiementService.deleteModeDePaiement(id);
        if (isDeleted) {
            return ResponseEntity.ok("Mode De Paiement with ID " + id + " was successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ModeDePaiement with ID " + id + " was not found.");
        }
    }

}
