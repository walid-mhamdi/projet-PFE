package ouachousoft.BackEnd0.controllerglobal;

import org.springframework.http.HttpStatus;
import ouachousoft.BackEnd0.entity.Pays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ouachousoft.BackEnd0.entity.Ville;
import ouachousoft.BackEnd0.serviceglobal.PaysService;

import java.util.List;

@RestController
@RequestMapping("/pays")
public class PaysController {

    @Autowired
    private PaysService paysService;

    @PostMapping
    public ResponseEntity<?> createPays(@RequestBody Pays pays) {

        if (pays.getCode() == null || pays.getLibelle() == null) {
            return ResponseEntity.badRequest().body("Le code et le nom de pays doivent être fournis.");
        }

        if (pays.getCode().isEmpty() || pays.getLibelle().isEmpty()) {
            return ResponseEntity.badRequest().body("Le code et le nom de pays ne peuvent pas être vides.");
        }

        Pays existingPaysByCode = paysService.getPaysByCode(pays.getCode());

        Pays existingPaysByName = paysService.getPaysByLibelle(pays.getLibelle());

        if (existingPaysByCode != null && existingPaysByName != null) {
            return ResponseEntity.badRequest().body(" le même code avec le même pays existe déjà.");
        }
        if (existingPaysByCode != null) {
            return ResponseEntity.badRequest().body(" le même code existe déjà.");
        }
        if (existingPaysByName != null) {
            return ResponseEntity.badRequest().body(" le même pays existe déjà.");
        }

        Pays savedPays = paysService.savePays(pays);
        return ResponseEntity.ok(savedPays);
    }



    @GetMapping
    public ResponseEntity<List<Pays>> getAllPays() {
        List<Pays> paysList = paysService.getAllPays();
        return ResponseEntity.ok(paysList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pays> getPaysById(@PathVariable int id) {
        Pays pays = paysService.getPaysById(id);
        if (pays != null) {
            return ResponseEntity.ok(pays);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePays(@PathVariable int id) {
        boolean isDeleted = paysService.deletePays(id);
        if (isDeleted) {
            return ResponseEntity.ok("Pays with ID " + id + " was successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pays with ID " + id + " was not found.");
        }
    }
}
