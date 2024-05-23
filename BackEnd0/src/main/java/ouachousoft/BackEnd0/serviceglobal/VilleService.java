package ouachousoft.BackEnd0.serviceglobal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ouachousoft.BackEnd0.entity.Ville;
import ouachousoft.BackEnd0.repositoryglobal.VilleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VilleService {

    @Autowired
    private VilleRepository villeRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public Ville getVilleByCode(String code) {
        return villeRepository.findByCode(code);
    }

    public Ville getVilleByVille(String ville) {
        return villeRepository.findByVille(ville);
    }

    public Ville saveVille(Ville ville) {
        return villeRepository.save(ville);
    }

    public List<Ville> getAllVilles() {
        return villeRepository.findAll();
    }

    public Ville getVilleById(int id) {
        return villeRepository.findById(id).orElse(null);
    }

    public boolean deleteVille(int id) {
        Optional<Ville> ville = villeRepository.findById(id);
        if (ville.isPresent()) {
            villeRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
