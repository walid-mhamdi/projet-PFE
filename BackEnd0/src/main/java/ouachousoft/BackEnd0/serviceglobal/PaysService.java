package ouachousoft.BackEnd0.serviceglobal;

import ouachousoft.BackEnd0.entity.Pays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ouachousoft.BackEnd0.repositoryglobal.PaysRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PaysService {

    @Autowired
    private PaysRepository paysRepository;

    public Pays savePays(Pays pays) {
        return paysRepository.save(pays);
    }

    public List<Pays> getAllPays() {
        return paysRepository.findAll();
    }

    public Pays getPaysById(int id) {
        return paysRepository.findById(id).orElse(null);
    }

public boolean deletePays(int id) {
    Optional<Pays> pays = paysRepository.findById(id);
    if (pays.isPresent()) {
        paysRepository.deleteById(id);
        return true;
    } else {
        return false;
    }
}
    public Pays getPaysByCode(String code) {
        return paysRepository.findByCode(code);
    }

    public Pays getPaysByLibelle(String libelle) {
        return paysRepository.findBylibelle(libelle);

    }
}
