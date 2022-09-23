package mail.lemeshev.server.service;

import lombok.AllArgsConstructor;
import mail.lemeshev.server.model.Message;
import mail.lemeshev.server.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {

    private MessageRepository repository;

    public Message saveMessage(Message message) {
        return repository.save(message);
    }

    public List<Message> getMessages() {
        return repository.getAllMessages();
    }

    public Message getMessageById(Integer id) {
        return repository.findById(id);
    }

    public void deleteMessage(Integer id) {
        repository.delete(id);
    }

    public Message updateMessage(Integer id, Message message) {
        return repository.update(id, message);
    }
}
