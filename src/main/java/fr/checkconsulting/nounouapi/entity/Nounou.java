package fr.checkconsulting.nounouapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Nounou {

    @Id
    private String email;
    private String nom;
    private String prenom;
    private String rue;
    private String codePostal;
    private String ville;
    private String numeroTelephone;
    private String pseudo;
}
