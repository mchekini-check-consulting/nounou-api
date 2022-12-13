package fr.checkconsulting.nounouapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DisponibiliteDTO {
    private int id;
    private int jour;
    private LocalTime dateDebutMatin;
    private LocalTime dateFinMatin;
    private LocalTime dateDebutMidi;
    private LocalTime dateFinMidi;
    private LocalTime dateDebutSoir;
    private LocalTime dateFinSoir;
    private String nounouId;
}
