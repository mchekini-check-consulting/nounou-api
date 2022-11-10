package fr.checkconsulting.nounouapi.services;

import fr.checkconsulting.nounouapi.repository.DisponibiliteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class DisponibiliteService {
    private final ModelMapper modelMapper;
    private final DisponibiliteRepository disponibiliteRepository;

    @Autowired
    public DisponibiliteService(ModelMapper modelMapper, DisponibiliteRepository disponibiliteRepository) {
        this.modelMapper = modelMapper;
        this.disponibiliteRepository = disponibiliteRepository;
    }
}
