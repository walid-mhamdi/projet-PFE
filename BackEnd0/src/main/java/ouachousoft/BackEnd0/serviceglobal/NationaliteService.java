package ouachousoft.BackEnd0.serviceglobal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ouachousoft.BackEnd0.dto.NationaliteDto;
import ouachousoft.BackEnd0.entity.Nationalite;
import ouachousoft.BackEnd0.exception.NationaliteAlreadyExistsException;
import ouachousoft.BackEnd0.exception.NationaliteNotFoundException;
import ouachousoft.BackEnd0.repositoryglobal.NationaliteRepository;

import java.util.List;

@Service
public class NationaliteService {

    private final NationaliteRepository nationaliteRepository;

    @Autowired
    public NationaliteService(NationaliteRepository nationaliteRepository) {
        this.nationaliteRepository = nationaliteRepository;
    }

    public Nationalite createNationalite(NationaliteDto nationaliteDto) throws NationaliteAlreadyExistsException {
        if (nationaliteRepository.findByCode(nationaliteDto.getCode()) != null) {
            throw new NationaliteAlreadyExistsException("Nationalité with code " + nationaliteDto.getCode() + " already exists.");
        }
        if (nationaliteRepository.findByLibelle(nationaliteDto.getLibelle()) != null) {
            throw new NationaliteAlreadyExistsException("Nationalité with libellé " + nationaliteDto.getLibelle() + " already exists.");
        }
        Nationalite nationalite = new Nationalite();
        nationalite.setCode(nationaliteDto.getCode());
        nationalite.setLibelle(nationaliteDto.getLibelle());
        return nationaliteRepository.save(nationalite);
    }

    public List<Nationalite> getAllNationalites() {
        return nationaliteRepository.findAll();
    }

    public Nationalite getNationaliteById(int id) throws NationaliteNotFoundException {
        return nationaliteRepository.findById(id)
                .orElseThrow(() -> new NationaliteNotFoundException("Nationalité with ID " + id + " not found."));
    }

    public void deleteNationalite(int id) throws NationaliteNotFoundException {
        if (!nationaliteRepository.existsById(id)) {
            throw new NationaliteNotFoundException("Nationalité with ID " + id + " not found.");
        }
        nationaliteRepository.deleteById(id);
    }

    public Nationalite updateNationalite(int id, NationaliteDto nationaliteDto) throws NationaliteNotFoundException {
        Nationalite existingNationalite = getNationaliteById(id);
        existingNationalite.setCode(nationaliteDto.getCode());
        existingNationalite.setLibelle(nationaliteDto.getLibelle());
        return nationaliteRepository.save(existingNationalite);
    }
}
