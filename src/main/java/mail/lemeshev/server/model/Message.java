package mail.lemeshev.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private int id;
    private String from;
    private String to;
    private String subject;
    private String text;
    private LocalDateTime date;
}
