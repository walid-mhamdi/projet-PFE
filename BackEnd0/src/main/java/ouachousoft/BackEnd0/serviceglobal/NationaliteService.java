package ouachousoft.BackEnd0.serviceglobal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ouachousoft.BackEnd0.entity.Nationalite;
import ouachousoft.BackEnd0.repositoryglobal.NationaliteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class NationaliteService {

    @Autowired
    private NationaliteRepository nationaliteRepository;

    public Nationalite saveNationalite(Nationalite nationalite) {
        return nationaliteRepository.save(nationalite);
    }

    public List<Nationalite> getAllNationalites() {
        return nationaliteRepository.findAll();
    }

    public Nationalite getNationaliteById(int id) {
        return nationaliteRepository.findById(id).orElse(null);
    }

    public Nationalite getNationaliteByCode(String code) {
        return nationaliteRepository.findByCode(code);
    }

    public Nationalite getNationaliteByLibelle(String libelle) {
        return nationaliteRepository.findByLibelle(libelle);
    }

    public boolean deleteNationalite(int id) {
        if (nationaliteRepository.existsById(id)) {
            nationaliteRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public Nationalite updateNationalite(Nationalite nationalite) {
        Optional<Nationalite> existingNationalite = nationaliteRepository.findById(nationalite.getId());
        if (existingNationalite.isPresent()) {
            return nationaliteRepository.save(nationalite);
        } else {
            throw new RuntimeException("Nationalite not found with id " + nationalite.getId());
        }
    }
}
