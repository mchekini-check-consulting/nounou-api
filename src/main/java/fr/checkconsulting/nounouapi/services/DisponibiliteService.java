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
import java.time.LocalDate;
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
                .map(this::mapToDisponibiliteDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<DisponibiliteDTO> createNounouDisponibilites(List<DisponibiliteDTO> listDisponibiliteDTO) {
        deleteDisponibilitesByNounouId();
        List<Disponibilite> result = disponibiliteRepository.saveAll(
                listDisponibiliteDTO
                        .stream()
                        .map(this::mapToDisponibilite)
                        .collect(Collectors.toList())
        );
        return result
                .stream()
                .map(this::mapToDisponibiliteDto)
                .collect(Collectors.toList());
    }

    private Disponibilite mapToDisponibilite(DisponibiliteDTO disponibiliteDTO){
        return Disponibilite.builder()
                .dateDebutMatin(disponibiliteDTO.getDate_debut_matin() != null ? disponibiliteDTO.getDate_debut_matin().toLocalTime() : null)
                .dateFinMatin(disponibiliteDTO.getDate_fin_matin() != null ? disponibiliteDTO.getDate_fin_matin().toLocalTime() : null)
                .dateDebutMidi(disponibiliteDTO.getDate_debut_midi() != null ? disponibiliteDTO.getDate_debut_midi().toLocalTime() : null)
                .dateFinMidi(disponibiliteDTO.getDate_fin_midi() != null ? disponibiliteDTO.getDate_fin_midi().toLocalTime() : null)
                .dateDebutSoir(disponibiliteDTO.getDate_debut_soir() != null ? disponibiliteDTO.getDate_debut_soir().toLocalTime() : null)
                .dateFinSoir(disponibiliteDTO.getDate_fin_soir() != null ? disponibiliteDTO.getDate_fin_soir().toLocalTime() : null)
                .jour(disponibiliteDTO.getJour())
                .id(disponibiliteDTO.getId())
                .nounouId(disponibiliteDTO.getNounouId())
                .build();
    }

    private DisponibiliteDTO mapToDisponibiliteDto(Disponibilite disponibilite){
        return DisponibiliteDTO.builder()
                .date_debut_matin(disponibilite.getDateDebutMatin() != null ? disponibilite.getDateDebutMatin().atDate(LocalDate.now()) : null)
                .date_fin_matin(disponibilite.getDateFinMatin() != null ? disponibilite.getDateFinMatin().atDate(LocalDate.now()) : null)
                .date_debut_midi(disponibilite.getDateDebutMidi() != null ? disponibilite.getDateDebutMidi().atDate(LocalDate.now()) : null)
                .date_fin_midi(disponibilite.getDateFinMidi() != null ? disponibilite.getDateFinMidi().atDate(LocalDate.now()) : null)
                .date_debut_soir(disponibilite.getDateDebutSoir() != null ? disponibilite.getDateDebutSoir().atDate(LocalDate.now()) : null)
                .date_fin_soir(disponibilite.getDateFinSoir() != null ? disponibilite.getDateFinSoir().atDate(LocalDate.now()) : null)
                .nounouId(disponibilite.getNounouId())
                .jour(disponibilite.getJour())
                .id(disponibilite.getId())
                .build();
    }

    @Transactional
    public void deleteDisponibilitesByNounouId() {
        Jwt user =  ((Jwt) SecurityContextHolder.getContext().getAuthentication().getCredentials());
        String nounouId = String.valueOf(user.getClaims().get("email"));
        disponibiliteRepository.deleteAllByNounouId(nounouId);
    }
}
