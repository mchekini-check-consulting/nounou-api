package fr.checkconsulting.nounouapi.resources;

import fr.checkconsulting.nounouapi.entity.Message;
import fr.checkconsulting.nounouapi.services.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/chat")
public class ChatResource {
    private final ChatService chatService;

    public ChatResource(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/get")
    public ResponseEntity<List<Message>> getMessages() throws Exception {
        return ResponseEntity.ok(chatService.getMessages());
    }

    @PostMapping("/send")
    public void sendMessage(@RequestBody Message message) throws Exception {
        message.setTimeMessage(LocalDateTime.now());
        chatService.sendMessage(message);
    }
}
