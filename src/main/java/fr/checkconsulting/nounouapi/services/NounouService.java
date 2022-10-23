package fr.checkconsulting.nounouapi.services;

import fr.checkconsulting.nounouapi.dto.NounouDTO;
import fr.checkconsulting.nounouapi.entity.Nounou;
import fr.checkconsulting.nounouapi.repository.NounouRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NounouService {


    private final NounouRepository nounouRepository;

    @Autowired
    public NounouService(NounouRepository nounouRepository) {
        this.nounouRepository = nounouRepository;
    }

    public List<NounouDTO> getAllNounous() {
        return nounouRepository
                .findAll()
                .stream()
                .map(NounouDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<NounouDTO> getNounouByEmail(String email) {
        return nounouRepository
                .findById(email)
                .map(NounouDTO::new);
    }

    public void updateNounou(Nounou famille) {
        nounouRepository.save(famille);
    }
}
