package fr.checkconsulting.nounouapi.dto;

import fr.checkconsulting.nounouapi.entity.Nounou;
import lombok.Data;

@Data
public class NounouDTO {
    private String nom;
    private String prenom;
    private String adresse;
    private String numeroTelephone;

    public NounouDTO(Nounou nounou) {
        nom = nounou.getNom();
        prenom = nounou.getPrenom();
        adresse = nounou.getAdresse();
        numeroTelephone = nounou.getNumeroTelephone();
    }
}
