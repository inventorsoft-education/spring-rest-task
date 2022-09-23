package mail.lemeshev.mail_server.service;

import mail.lemeshev.mail_server.model.Message;
import mail.lemeshev.mail_server.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository repository;

    public Message saveMessage(Message message) {
        return repository.save(message);
    }

    public List<Message> getMessages() {
        return repository.getAllMessages();
    }

    public Message getMessageById(int id) {
        return repository.findById(id);
    }

    public String deleteMessage(int id) {
        repository.delete(id);
        return "Message removed !! " + id;
    }

    public Message updateMessage(Integer id, Message message) {
        return repository.update(id, message);
    }
}
