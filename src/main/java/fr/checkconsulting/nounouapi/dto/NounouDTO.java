package fr.checkconsulting.nounouapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NounouDTO {
    private String nom;
    private String prenom;
    private String rue;
    private String codePostal;
    private String ville;
    private String numeroTelephone;
    private String email;
}
