package fr.checkconsulting.nounouapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Disponibilite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int jour;
    private LocalDateTime date_debut_matin;
    private LocalDateTime date_fin_matin;
    private LocalDateTime date_debut_midi;
    private LocalDateTime date_fin_midi;
    private LocalDateTime date_debut_soir;
    private LocalDateTime date_fin_soir;
    @ManyToOne
    @JoinColumn(name = "nounouId")
    private Nounou nounouId;
}
