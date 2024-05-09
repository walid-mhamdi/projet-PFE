package ouachousoft.BackEnd0.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ouachousoft.BackEnd0.entity.Avis;
import ouachousoft.BackEnd0.service.AvisService;

@AllArgsConstructor
@RequestMapping("avis")
@RestController
public class AvisControleur {

    private final AvisService avisService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void creer(@RequestBody Avis avis){
        this.avisService.creer(avis);
    }
}
