package ouachousoft.BackEnd0.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ouachousoft.BackEnd0.entity.Avis;
import ouachousoft.BackEnd0.repository.AvisRepository;

@AllArgsConstructor
@Service
public class AvisService {

    private final AvisRepository avisRepository;

    public void creer(Avis avis){
        this.avisRepository.save(avis);
    }
}
