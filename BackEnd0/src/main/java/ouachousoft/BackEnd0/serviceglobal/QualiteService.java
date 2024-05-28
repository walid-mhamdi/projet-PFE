package ouachousoft.BackEnd0.serviceglobal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ouachousoft.BackEnd0.entity.Qualite;
import ouachousoft.BackEnd0.repositoryglobal.QualiteRepository;

import java.util.List;

@Service
public class QualiteService {

    @Autowired
    private QualiteRepository qualiteRepository;

    public Qualite saveQualite(Qualite qualite) {
        return qualiteRepository.save(qualite);
    }

    public List<Qualite> getAllQualites() {
        return qualiteRepository.findAll();
    }

    public Qualite getQualiteById(int id) {
        return qualiteRepository.findById(id).orElse(null);
    }

    public Qualite getQualiteByCode(String code) {
        return qualiteRepository.findByCode(code);
    }

    public Qualite getQualiteByLibelle(String libelle) {
        return qualiteRepository.findByLibelle(libelle);
    }

    public boolean deleteQualite(int id) {
        if (qualiteRepository.existsById(id)) {
            qualiteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
