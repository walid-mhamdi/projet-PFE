package ouachousoft.BackEnd0.controllerglobal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ouachousoft.BackEnd0.entity.Qualite;
import ouachousoft.BackEnd0.serviceglobal.QualiteService;

import java.util.List;

@RestController
@RequestMapping("/qualite")
@CrossOrigin(origins = "http://localhost:4200",methods ={ RequestMethod.GET,RequestMethod.DELETE,RequestMethod.POST,RequestMethod.PUT})  // Ajout de cette ligne pour permettre les requêtes CORS depuis localhost:4200

public class QualiteController {

    private final QualiteService qualiteService;

    public QualiteController(QualiteService qualiteService) {
        this.qualiteService = qualiteService;
    }

    @PostMapping
    public ResponseEntity<?> createQualite(@RequestBody Qualite qualite) {
        if (qualite.getCode() == null || qualite.getLibelle() == null) {
            return ResponseEntity.badRequest().body("Le code et le libellé de la qualité doivent être fournis.");
        }

        if (qualite.getCode().isEmpty() || qualite.getLibelle().isEmpty()) {
            return ResponseEntity.badRequest().body("Le code et le libellé de la qualité ne peuvent pas être vides.");
        }

        Qualite existingQualiteByCode = qualiteService.getQualiteByCode(qualite.getCode());
        Qualite existingQualiteByLibelle = qualiteService.getQualiteByLibelle(qualite.getLibelle());

        if (existingQualiteByCode != null && existingQualiteByLibelle != null) {
            return ResponseEntity.badRequest().body("Une qualité avec le même code et le même libellé existe déjà.");
        }
        if (existingQualiteByCode != null) {
            return ResponseEntity.badRequest().body("Une qualité avec le même code existe déjà.");
        }
        if (existingQualiteByLibelle != null) {
            return ResponseEntity.badRequest().body("Une qualité avec le même libellé existe déjà.");
        }

        Qualite savedQualite = qualiteService.saveQualite(qualite);
        return ResponseEntity.ok(savedQualite);
    }

    @GetMapping
    public ResponseEntity<List<Qualite>> getAllQualites() {
        List<Qualite> qualiteList = qualiteService.getAllQualites();
        return ResponseEntity.ok(qualiteList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Qualite> getQualiteById(@PathVariable int id) {
        Qualite qualite = qualiteService.getQualiteById(id);
        if (qualite != null) {
            return ResponseEntity.ok(qualite);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQualite(@PathVariable int id) {
        boolean isDeleted = qualiteService.deleteQualite(id);
        if (isDeleted) {
            return ResponseEntity.ok("Qualité with ID " + id + " was successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Qualité with ID " + id + " was not found.");
        }
    }
}
