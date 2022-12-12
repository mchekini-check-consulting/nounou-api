package fr.checkconsulting.nounouapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Disponibilite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
