package mail.lemeshev.server.repository;

import mail.lemeshev.server.model.Message;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;

@Repository
public class MessageRepository {
    private ArrayList<Message> list;

    /*
     * Its database
     */ {
        String from = "ovb1m15.lyemeshev@kpnu.edu.ua";
        list = new ArrayList<>();
        list.add(new Message(1, from, "test@gmail.com", "Hello1", "Hello its body message.1", LocalDateTime.parse("2022-09-23T14:00:00")));
        list.add(new Message(2, from, "test@gmail.com", "Hello2", "Hello its body message.2", LocalDateTime.parse("2022-09-23T14:05:00")));
        list.add(new Message(3, from, "test@gmail.com", "Hello3", "Hello its body message.3", LocalDateTime.parse("2022-09-23T14:10:00")));
        list.add(new Message(4, from, "test@gmail.com", "Hello4", "Hello its body message.4", LocalDateTime.parse("2022-09-23T14:15:00")));
    }

    public List<Message> getAllMessages() {
        return list;
    }

    public Message save(Message message) {
        list.add(message);
        return message;
    }

    public Message findById(Integer id) {
        return list.stream()
                .filter(message -> message.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Message with id: " + id + " not found!"));
    }

    public void delete(Integer id) {
        list.removeIf(x -> x.getId() == id);
    }

    public Message update(Integer id, Message message) {
        Optional.of(findById(id)).ifPresent(i -> list.set(id, message));
        return message;
    }
}
