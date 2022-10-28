package fr.checkconsulting.nounouapi.dto;

import lombok.Data;

@Data
public class NounouDTO {
    private String email;
    private String pseudo;
    private String nom;
    private String prenom;
    private String adresse;
    private String numeroTelephone;
}
