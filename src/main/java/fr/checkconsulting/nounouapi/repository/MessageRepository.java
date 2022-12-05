package fr.checkconsulting.nounouapi.repository;

import fr.checkconsulting.nounouapi.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {
    List<Message> getAllByEmailSourceOrEmailDest(String emailSrc, String emailDest);
}
