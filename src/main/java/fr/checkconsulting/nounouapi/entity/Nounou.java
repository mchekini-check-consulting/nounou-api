package fr.checkconsulting.nounouapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Nounou {

    @Id
    private String email;
    private String nom;
    private String prenom;
    private String adresse;
    private String numeroTelephone;
    private String pseudo;
}
