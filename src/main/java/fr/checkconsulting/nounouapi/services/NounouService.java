package fr.checkconsulting.nounouapi.services;

import fr.checkconsulting.nounouapi.entity.Nounou;
import fr.checkconsulting.nounouapi.repository.NounouRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NounouService {


    private final NounouRepository nounouRepository;

    @Autowired
    public NounouService(NounouRepository nounouRepository) {
        this.nounouRepository = nounouRepository;
    }

    public List<Nounou> getAllNounous() {
        return nounouRepository.findAll();
    }

    public Nounou getNounouByEmail(String email) {
        return nounouRepository.findById(email).get();
    }

    public void updateNounou(Nounou famille) {
        nounouRepository.save(famille);
    }
}
