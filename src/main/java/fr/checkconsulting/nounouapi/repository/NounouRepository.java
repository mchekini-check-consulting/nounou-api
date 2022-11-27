package fr.checkconsulting.nounouapi.repository;

import fr.checkconsulting.nounouapi.entity.Nounou;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NounouRepository extends JpaRepository<Nounou, String> {


    @Query("select n from Nounou n where (:nom is null or n.nom = :nom) and (:prenom is null or n.prenom = :prenom) and (:ville is null or n.ville = :ville)")
    List<Nounou> getNounousByCriteria(@Param("nom") String nom, @Param("prenom") String prenom, @Param("ville") String ville);

}
