package ouachousoft.BackEnd0.controllerglobal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ouachousoft.BackEnd0.dto.NationaliteDto;
import ouachousoft.BackEnd0.entity.Nationalite;
import ouachousoft.BackEnd0.serviceglobal.NationaliteService;
import ouachousoft.BackEnd0.exception.NationaliteAlreadyExistsException;
import ouachousoft.BackEnd0.exception.NationaliteNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/nationalite")
@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT})
public class NationaliteController {

    private final NationaliteService nationaliteService;

    public NationaliteController(NationaliteService nationaliteService) {
        this.nationaliteService = nationaliteService;
    }

    @PostMapping
    public ResponseEntity<?> createNationalite(@RequestBody NationaliteDto nationaliteDto) {
        try {
            Nationalite savedNationalite = nationaliteService.createNationalite(nationaliteDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedNationalite);
        } catch (NationaliteAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Nationalite>> getAllNationalites() {
        List<Nationalite> nationaliteList = nationaliteService.getAllNationalites();
        return ResponseEntity.ok(nationaliteList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNationaliteById(@PathVariable int id) {
        try {
            Nationalite nationalite = nationaliteService.getNationaliteById(id);
            return ResponseEntity.ok(nationalite);
        } catch (NationaliteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNationalite(@PathVariable int id) {
        try {
            nationaliteService.deleteNationalite(id);
            return ResponseEntity.ok("Nationalité with ID " + id + " was successfully deleted.");
        } catch (NationaliteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNationalite(@PathVariable int id, @RequestBody NationaliteDto nationaliteDto) {
        try {
            Nationalite updatedNationalite = nationaliteService.updateNationalite(id, nationaliteDto);
            return ResponseEntity.ok(updatedNationalite);
        } catch (NationaliteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
