package fr.checkconsulting.nounouapi.config;

import fr.checkconsulting.nounouapi.entity.Message;
import fr.checkconsulting.nounouapi.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumer {

    private final MessageRepository messageRepository;

    @Autowired
    public KafkaConsumer(MessageRepository messageRepository) {this.messageRepository = messageRepository;}

    @KafkaListener(topics = "chat-famille", groupId = "nounou-group-id")
    public void listenSenderMessage(Message data) {
        System.out.println("Message received by consumer : " + data);
        // Sauvegarder une copie sur la base de données famille
        messageRepository.save(data);
    }
}
