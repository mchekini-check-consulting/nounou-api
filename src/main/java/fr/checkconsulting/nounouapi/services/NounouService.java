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


    public Optional<Nounou> updateNounou(String email, Nounou nounouRequestBody) {
        Optional<Nounou> nounou = nounouRepository.findById(email);
        nounou.ifPresent(nounou1 -> {
            nounou1.setNom(nounouRequestBody.getNom());
            nounou1.setPrenom(nounouRequestBody.getPrenom());
            nounou1.setAdresse(nounouRequestBody.getAdresse());
            nounou1.setNumeroTelephone(nounouRequestBody.getNumeroTelephone());
            nounouRepository.save(nounou1);
        });
        return nounou;
    }


}
