package ouachousoft.BackEnd0.controllerglobal;

import ouachousoft.BackEnd0.entity.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ouachousoft.BackEnd0.serviceglobal.VilleService;

import java.util.List;

@RestController
@RequestMapping("/ville")
@CrossOrigin(origins = "http://localhost:4200",methods ={ RequestMethod.GET,RequestMethod.DELETE,RequestMethod.POST,RequestMethod.PUT})  // Ajout de cette ligne pour permettre les requêtes CORS depuis localhost:4200

public class VilleController {

    @Autowired
    private VilleService villeService;


    @PostMapping
    public ResponseEntity<?> createVille(@RequestBody Ville ville) {

        if (ville.getCode() == null || ville.getVille() == null) {
            return ResponseEntity.badRequest().body("Le code et le nom de ville doivent être fournis.");
        }

        if (ville.getCode().isEmpty() || ville.getVille().isEmpty()) {
            return ResponseEntity.badRequest().body("Le code et le nom de ville ne peuvent pas être vides.");
        }

        Ville existingVilleByCode = villeService.getVilleByCode(ville.getCode());
        Ville existingVilleByVille = villeService.getVilleByVille(ville.getVille());

        if (existingVilleByCode != null && existingVilleByVille != null) {
            return ResponseEntity.badRequest().body("Le même code avec la même ville existe déjà.");
        }
        if (existingVilleByCode != null) {
            return ResponseEntity.badRequest().body("Le même code existe déjà.");
        }
        if (existingVilleByVille != null) {
            return ResponseEntity.badRequest().body("La même ville existe déjà.");
        }

        Ville savedVille = villeService.saveVille(ville);
        return ResponseEntity.ok(savedVille);
    }
    @GetMapping
    public ResponseEntity<List<Ville>> getAllVilles() {
        List<Ville> villeList = villeService.getAllVilles();
        return ResponseEntity.ok(villeList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ville> getVilleById(@PathVariable int id) {
        Ville ville = villeService.getVilleById(id);
        if (ville != null) {
            return ResponseEntity.ok(ville);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVille(@PathVariable int id) {
        boolean isDeleted = villeService.deleteVille(id);
        if (isDeleted) {
            return ResponseEntity.ok("Ville with ID " + id + " was successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ville with ID " + id + " was not found.");
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateVille(@PathVariable int id, @RequestBody Ville ville) {
        Ville existingVille = villeService.getVilleById(id);
        if (existingVille != null) {
            ville.setId(id); // Assurez-vous que l'ID de la ville est correctement défini
            Ville updatedVille = villeService.updateVille(ville);
            return ResponseEntity.ok(updatedVille);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
