package ouachousoft.BackEnd0.controllerglobal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ouachousoft.BackEnd0.entity.Banque;
import ouachousoft.BackEnd0.serviceglobal.BanqueService;

import java.util.List;

@RestController
@RequestMapping("/banque")
@CrossOrigin(origins = "http://localhost:4200",methods ={ RequestMethod.GET,RequestMethod.DELETE,RequestMethod.POST,RequestMethod.PUT})  // Ajout de cette ligne pour permettre les requêtes CORS depuis localhost:4200

public class BanqueController {

    private final BanqueService banqueService;

    public BanqueController(BanqueService banqueService) {
        this.banqueService = banqueService;
    }

    @PostMapping
    public ResponseEntity<?> createBanque(@RequestBody Banque banque) {
        if (banque.getCode() == null || banque.getLibelle() == null) {
            return ResponseEntity.badRequest().body("Le code et le libellé de la banque doivent être fournis.");
        }

        if (banque.getCode().isEmpty() || banque.getLibelle().isEmpty()) {
            return ResponseEntity.badRequest().body("Le code et le libellé de la banque ne peuvent pas être vides.");
        }

        Banque existingBanqueByCode = banqueService.getBanqueByCode(banque.getCode());

        Banque existingBanqueByLibelle = banqueService.getBanqueByLibelle(banque.getLibelle());

        if (existingBanqueByCode != null && existingBanqueByLibelle != null) {
            return ResponseEntity.badRequest().body("Une banque avec le même code et le même libellé existe déjà.");
        }
        if (existingBanqueByCode != null) {
            return ResponseEntity.badRequest().body("Une banque avec le même code existe déjà.");
        }
        if (existingBanqueByLibelle != null) {
            return ResponseEntity.badRequest().body("Une banque avec le même libellé existe déjà.");
        }

        Banque savedBanque = banqueService.saveBanque(banque);
        return ResponseEntity.ok(savedBanque);
    }

    @GetMapping
    public ResponseEntity<List<Banque>> getAllBanques() {
        List<Banque> banqueList = banqueService.getAllBanques();
        return ResponseEntity.ok(banqueList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Banque> getBanqueById(@PathVariable int id) {
        Banque banque = banqueService.getBanqueById(id);
        if (banque != null) {
            return ResponseEntity.ok(banque);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBanque(@PathVariable int id) {
        boolean isDeleted = banqueService.deleteBanque(id);
        if (isDeleted) {
            return ResponseEntity.ok("Banque with ID " + id + " was successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Banque with ID " + id + " was not found.");
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBanque(@PathVariable int id, @RequestBody Banque banque) {
        Banque existingBanque = banqueService.getBanqueById(id);
        if (existingBanque != null) {
            banque.setId(id); // Assurez-vous que l'ID de la banque est correctement défini
            Banque updatedBanque = banqueService.updateBanque(banque);
            return ResponseEntity.ok(updatedBanque);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
