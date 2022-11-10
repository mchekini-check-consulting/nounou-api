package fr.checkconsulting.nounouapi.repository;

import fr.checkconsulting.nounouapi.entity.Disponibilite;
import fr.checkconsulting.nounouapi.entity.DisponibiliteId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisponibiliteRepository extends JpaRepository<Disponibilite, DisponibiliteId> {
}
