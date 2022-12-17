package fr.checkconsulting.nounouapi.services;

import fr.checkconsulting.nounouapi.dto.NounouDTO;
import fr.checkconsulting.nounouapi.entity.Nounou;
import fr.checkconsulting.nounouapi.repository.NounouRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
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


    public Optional<NounouDTO> getNounouByEmail() {
        Jwt user = ((Jwt) SecurityContextHolder.getContext().getAuthentication().getCredentials());
        String email = String.valueOf(user.getClaims().get("email"));
        return nounouRepository
                .findById(email)
                .map(nounou -> modelMapper.map(nounou, NounouDTO.class));
    }


    public Optional<Nounou> updateNounou(Nounou nounouRequestBody) {
        Optional<Nounou> nounou = nounouRepository.findById(nounouRequestBody.getEmail());
        nounou.ifPresent(nounou1 -> {
            nounou1.setNom(nounouRequestBody.getNom());
            nounou1.setPrenom(nounouRequestBody.getPrenom());
            nounou1.setRue(nounouRequestBody.getRue());
            nounou1.setVille(nounouRequestBody.getVille());
            nounou1.setCodePostal(nounouRequestBody.getCodePostal());
            nounou1.setNumeroTelephone(nounouRequestBody.getNumeroTelephone());
            nounouRepository.save(nounou1);
        });
        return nounou;
    }


}
