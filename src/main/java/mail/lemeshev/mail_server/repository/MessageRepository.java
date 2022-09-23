package mail.lemeshev.mail_server.repository;

import mail.lemeshev.mail_server.model.Message;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MessageRepository {
    private ArrayList<Message> list;

    /*
     * Its database
     */ {
        String from = "ovb1m15.lyemeshev@kpnu.edu.ua";
        list = new ArrayList<>();
        list.add(new Message(1, from, "lemeshevolex@gmail.com", "Hello1", "Hello its body message.1", "22/09/2022 14:00:00"));
        list.add(new Message(2, from, "lemeshevolex@gmail.com", "Hello2", "Hello its body message.2", "22/09/2022 14:05:00"));
        list.add(new Message(3, from, "lemeshevolex@gmail.com", "Hello3", "Hello its body message.3", "22/09/2022 14:10:00"));
        list.add(new Message(4, from, "lemeshevolex@gmail.com", "Hello4", "Hello its body message.4", "22/09/2022 14:15:00"));
    }

    public List<Message> getAllMessages() {
        return list;
    }


    public Message save(Message message) {
        list.add(message);
        return message;
    }

    public Message findById(int id) {
        return list.stream()
                .filter(message -> message.getId() == (id))
                .findFirst()
                .orElse(null);
    }

    public void delete(int id) {
        list.removeIf(x -> x.getId() == (id));
    }

    public Message update(Integer id, Message message) {
        if (findById(id) != null) {
            list.set(id, message);
            return message;
        } else return null;
    }
}
