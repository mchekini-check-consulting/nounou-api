package fr.checkconsulting.nounouapi.repository;

import fr.checkconsulting.nounouapi.entity.Nounou;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NounouRepository extends JpaRepository<Nounou, String> {
    @Query("select distinct n from Nounou n inner join Disponibilite d on n.email = d.nounouId  where " +
            "(:nom is null or n.nom = :nom) " +
            "and (:prenom is null or n.prenom = :prenom) " +
            "and(:ville is null or n.ville = :ville) " +
            "and (:jour is null or d.jour = : jour) " +
            "and (:debut is null or ( extract(hour from  d.date_debut_matin)  between   :debut   and  :fin  ) )" +
            "and  :fin is  null " +
            "or (extract( hour from d.date_fin_soir) between  :debut and   :fin) " +
            "")
    List<Nounou> getNounousByCriteria(@Param("nom") String nom,
                                      @Param("prenom") String prenom,
                                      @Param("ville") String ville,
                                      @Param("debut") Integer debut,
                                      @Param("fin")Integer fin,
                                      @Param("jour")Integer jour );
}
