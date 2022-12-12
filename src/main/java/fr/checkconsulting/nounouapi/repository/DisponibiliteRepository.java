package fr.checkconsulting.nounouapi.repository;

import fr.checkconsulting.nounouapi.entity.Disponibilite;
import fr.checkconsulting.nounouapi.entity.Nounou;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisponibiliteRepository extends JpaRepository<Disponibilite, Integer> {
    List<Disponibilite> findAllByNounouId(Nounou nounouId);

    void deleteAllByNounouId(String id);

    List<Disponibilite> findAllByNounouId(String email);
}
