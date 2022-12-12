package fr.checkconsulting.nounouapi.repository;

import fr.checkconsulting.nounouapi.entity.Disponibilite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisponibiliteRepository extends JpaRepository<Disponibilite, Integer> {
    List<Disponibilite> findAllByNounouId(String id);

    void deleteAllByNounouId(String id);

    @Query("select d from Disponibilite d where (:jour = -1 or d.jour = :jour)")
    List<Disponibilite> findAllByJour(@Param("jour") int jour);
}
