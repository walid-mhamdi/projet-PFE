package ouachousoft.BackEnd0.serviceglobal;

import org.springframework.stereotype.Service;
import ouachousoft.BackEnd0.entity.Banque;
import ouachousoft.BackEnd0.repositoryglobal.BanqueRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BanqueService {

    private final BanqueRepository banqueRepository;

    public BanqueService(BanqueRepository banqueRepository) {
        this.banqueRepository = banqueRepository;
    }

    public Banque saveBanque(Banque banque) {
        return banqueRepository.save(banque);
    }

    public List<Banque> getAllBanques() {
        return banqueRepository.findAll();
    }

    public Banque getBanqueById(int id) {
        return banqueRepository.findById(id).orElse(null);
    }

    public Banque getBanqueByCode(String code) {
        return banqueRepository.findByCode(code);
    }

    public Banque getBanqueByLibelle(String libelle) {
        return banqueRepository.findByLibelle(libelle);
    }

    public boolean deleteBanque(int id) {
        if (banqueRepository.existsById(id)) {
            banqueRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public Banque updateBanque(Banque banque) {
        Optional<Banque> existingBanque = banqueRepository.findById(banque.getId());
        if (existingBanque.isPresent()) {
            return banqueRepository.save(banque);
        } else {
            throw new RuntimeException("Banque not found with id " + banque.getId());
        }
    }
}
