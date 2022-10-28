package fr.checkconsulting.nounouapi.services;

import fr.checkconsulting.nounouapi.dto.NounouDTO;
import fr.checkconsulting.nounouapi.entity.Nounou;
import fr.checkconsulting.nounouapi.repository.NounouRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NounouService {

    private final ModelMapper modelMapper;

    private final NounouRepository nounouRepository;

    @Autowired
    public NounouService(ModelMapper modelMapper, NounouRepository nounouRepository) {
        this.modelMapper = modelMapper;
        this.nounouRepository = nounouRepository;
    }

    public List<NounouDTO> getAllNounous() {
        return nounouRepository
                .findAll()
                .stream()
                .map(nounou -> modelMapper.map(nounou, NounouDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<NounouDTO> getNounouByEmail(String email) {
        return nounouRepository
                .findById(email)
                .map(nounou -> modelMapper.map(nounou, NounouDTO.class));
    }

    public void updateNounou(Nounou famille) {
        nounouRepository.save(famille);
    }
}
