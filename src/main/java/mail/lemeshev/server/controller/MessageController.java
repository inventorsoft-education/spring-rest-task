package mail.lemeshev.server.controller;

import lombok.AllArgsConstructor;
import mail.lemeshev.server.model.Message;
import mail.lemeshev.server.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
@AllArgsConstructor
public class MessageController {

    private MessageService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Message addMessage(@RequestBody @Valid Message message) {
        return service.saveMessage(message);
    }

    @GetMapping
    public List<Message> findAllMessages() {
        return service.getMessages();
    }

    @GetMapping("/{id}")
    public Message findMessageById(@PathVariable Integer id) {
        return service.getMessageById(id);
    }

    @PutMapping("/{id}")
    public Message updateMessage(@PathVariable Integer id, @RequestBody @Valid Message message) {
        return service.updateMessage(id, message);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMessage(@PathVariable Integer id) {
        service.deleteMessage(id);
    }
}
