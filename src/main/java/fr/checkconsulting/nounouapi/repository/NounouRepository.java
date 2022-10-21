package fr.checkconsulting.nounouapi.repository;

import fr.checkconsulting.nounouapi.entity.Nounou;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NounouRepository extends JpaRepository<Nounou, String> {
}
