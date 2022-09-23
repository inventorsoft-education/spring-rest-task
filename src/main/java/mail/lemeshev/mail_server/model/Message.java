package mail.lemeshev.mail_server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private int id;
    private String from;
    private String to;
    private String subject;
    private String text;
    private String date;
}
