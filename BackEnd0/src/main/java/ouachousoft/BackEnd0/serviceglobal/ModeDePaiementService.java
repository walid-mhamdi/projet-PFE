package ouachousoft.BackEnd0.serviceglobal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ouachousoft.BackEnd0.entity.ModeDePaiement;
import ouachousoft.BackEnd0.repositoryglobal.ModeDePaiementRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ModeDePaiementService {

    @Autowired
    private ModeDePaiementRepository modeDePaiementRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public ModeDePaiement saveModeDePaiement(ModeDePaiement modeDePaiement) {
        return modeDePaiementRepository.save(modeDePaiement);
    }

    public List<ModeDePaiement> getAllModeDePaiement() {
        return modeDePaiementRepository.findAll();
    }

    public ModeDePaiement getModeDePaiementById(int id) {
        return modeDePaiementRepository.findById(id).orElse(null);
    }

    public boolean deleteModeDePaiement(int id) {
        Optional<ModeDePaiement> modeDePaiement = modeDePaiementRepository.findById(id);
        if (modeDePaiement.isPresent()) {
            modeDePaiementRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }


}
