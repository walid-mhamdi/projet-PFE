package ouachousoft.BackEnd0.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ouachousoft.BackEnd0.entity.Avis;
import ouachousoft.BackEnd0.repository.AvisRepository;

@AllArgsConstructor
@Service
public class AvisService {

    private final AvisRepository avisRepository;

    public void creer(Avis avis) {
        try {
            this.avisRepository.save(avis);
        } catch (Exception e) {
            throw new RuntimeException("Échec de l'enregistrement de l'avis.");
        }
    }
}