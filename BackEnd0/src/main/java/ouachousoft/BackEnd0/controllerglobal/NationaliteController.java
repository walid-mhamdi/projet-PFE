package ouachousoft.BackEnd0.controllerglobal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ouachousoft.BackEnd0.entity.Nationalite;
import ouachousoft.BackEnd0.serviceglobal.NationaliteService;

import java.util.List;

@RestController
@RequestMapping("/nationalite")
@CrossOrigin(origins = "http://localhost:4200",methods ={ RequestMethod.GET,RequestMethod.DELETE,RequestMethod.POST,RequestMethod.PUT})  // Ajout de cette ligne pour permettre les requêtes CORS depuis localhost:4200

public class NationaliteController {

    private final NationaliteService nationaliteService;

    public NationaliteController(NationaliteService nationaliteService) {
        this.nationaliteService = nationaliteService;
    }

    @PostMapping
    public ResponseEntity<?> createNationalite(@RequestBody Nationalite nationalite) {
        if (nationalite.getCode() == null || nationalite.getLibelle() == null) {
            return ResponseEntity.badRequest().body("Le code et le libellé de la nationalité doivent être fournis.");
        }

        if (nationalite.getCode().isEmpty() || nationalite.getLibelle().isEmpty()) {
            return ResponseEntity.badRequest().body("Le code et le libellé de la nationalité ne peuvent pas être vides.");
        }

        Nationalite existingNationaliteByCode = nationaliteService.getNationaliteByCode(nationalite.getCode());

        Nationalite existingNationaliteByLibelle = nationaliteService.getNationaliteByLibelle(nationalite.getLibelle());

        if (existingNationaliteByCode != null && existingNationaliteByLibelle != null) {
            return ResponseEntity.badRequest().body("Une nationalité avec le même code et le même libellé existe déjà.");
        }
        if (existingNationaliteByCode != null) {
            return ResponseEntity.badRequest().body("Une nationalité avec le même code existe déjà.");
        }
        if (existingNationaliteByLibelle != null) {
            return ResponseEntity.badRequest().body("Une nationalité avec le même libellé existe déjà.");
        }

        Nationalite savedNationalite = nationaliteService.saveNationalite(nationalite);
        return ResponseEntity.ok(savedNationalite);
    }

    @GetMapping
    public ResponseEntity<List<Nationalite>> getAllNationalites() {
        List<Nationalite> nationaliteList = nationaliteService.getAllNationalites();
        return ResponseEntity.ok(nationaliteList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Nationalite> getNationaliteById(@PathVariable int id) {
        Nationalite nationalite = nationaliteService.getNationaliteById(id);
        if (nationalite != null) {
            return ResponseEntity.ok(nationalite);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNationalite(@PathVariable int id) {
        boolean isDeleted = nationaliteService.deleteNationalite(id);
        if (isDeleted) {
            return ResponseEntity.ok("Nationalité with ID " + id + " was successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nationalité with ID " + id + " was not found.");
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateNationalite(@PathVariable int id, @RequestBody Nationalite nationalite) {
        Nationalite existingNationalite = nationaliteService.getNationaliteById(id);
        if (existingNationalite == null) {
            return ResponseEntity.notFound().build();
        }

        nationalite.setId(id); // Assure que l'ID de la nationalité à mettre à jour est correct
        Nationalite updatedNationalite = nationaliteService.updateNationalite(nationalite);
        return ResponseEntity.ok(updatedNationalite);
    }
}
