package fr.checkconsulting.nounouapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FamilleDTO {
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String mail;
}
