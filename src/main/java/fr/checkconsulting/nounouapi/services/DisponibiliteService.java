package fr.checkconsulting.nounouapi.services;

import fr.checkconsulting.nounouapi.dto.DisponibiliteDTO;
import fr.checkconsulting.nounouapi.repository.DisponibiliteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
