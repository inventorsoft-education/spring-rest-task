package mail.lemeshev.mail_server.controller;

import mail.lemeshev.mail_server.model.Message;
import mail.lemeshev.mail_server.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {
    @Autowired
    private MessageService service;

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public Message addMessage(@RequestBody Message message) {
        return service.saveMessage(message);
    }

    @GetMapping
    public List<Message> findAllMessages() {
        return service.getMessages();
    }

    @GetMapping("/{id}")
    public Message findMessageById(@PathVariable int id) {
        return service.getMessageById(id);
    }

    @PutMapping("/{id}")
    public Message updateMessage( @PathVariable int id
            ,@RequestBody Message message) {
        return service.updateMessage(id,message);
    }

    @DeleteMapping("/{id}")
    public String deleteMessage(@PathVariable int id) {
        return service.deleteMessage(id);
    }
}
