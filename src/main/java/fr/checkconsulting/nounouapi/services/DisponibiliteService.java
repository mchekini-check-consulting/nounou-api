package fr.checkconsulting.nounouapi.services;

import fr.checkconsulting.nounouapi.dto.DisponibiliteDTO;
import fr.checkconsulting.nounouapi.entity.Disponibilite;
import fr.checkconsulting.nounouapi.repository.DisponibiliteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DisponibiliteService {
    private final ModelMapper modelMapper;
    private final DisponibiliteRepository disponibiliteRepository;

    @Autowired
    public DisponibiliteService(ModelMapper modelMapper, DisponibiliteRepository disponibiliteRepository) {
        this.modelMapper = modelMapper;
        this.disponibiliteRepository = disponibiliteRepository;
    }

    public List<DisponibiliteDTO> getAllDisponibilitesByNounouId(String nounouId) {
        return disponibiliteRepository
                .findAllByNounouId(nounouId)
                .stream()
                .map(disponibilite -> modelMapper.map(disponibilite, DisponibiliteDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<DisponibiliteDTO> createNounouDisponibilites(List<DisponibiliteDTO> listDisponibiliteDTO) {
        deleteDisponibilitesByNounouId();
        List<Disponibilite> result = disponibiliteRepository.saveAll(
                listDisponibiliteDTO
                        .stream()
                        .map(disponibiliteDTO -> modelMapper.map(disponibiliteDTO, Disponibilite.class))
                        .collect(Collectors.toList())
        );
        return result
                .stream()
                .map(disponibilite -> modelMapper.map(disponibilite, DisponibiliteDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteDisponibilitesByNounouId() {
        Jwt user =  ((Jwt) SecurityContextHolder.getContext().getAuthentication().getCredentials());
        String nounouId = String.valueOf(user.getClaims().get("email"));
        disponibiliteRepository.deleteAllByNounouId(nounouId);
    }
}
