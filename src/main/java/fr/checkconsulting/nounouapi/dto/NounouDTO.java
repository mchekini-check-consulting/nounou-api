package fr.checkconsulting.nounouapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NounouDTO {
    private String email;
    private String pseudo;
    private String nom;
    private String prenom;
    private String adresse;
    private String numeroTelephone;
}
