package fr.checkconsulting.nounouapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Disponibilite {
    @Id
    private int id;
    private int jour;
    private LocalDateTime date_debut_matin;
    private LocalDateTime date_fin_matin;
    private LocalDateTime date_debut_midi;
    private LocalDateTime date_fin_midi;
    private LocalDateTime date_debut_soir;
    private LocalDateTime date_fin_soir;
    private String nounouId;
}
