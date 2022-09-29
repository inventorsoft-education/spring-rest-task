package main;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Email {
    Long id;
    @Size(min = 5, message = "Email must be min 5 symbols")
    String from;
    @Size(min = 5, message = "Email must be min 5 symbols")
    String to;
    String title;
    String body;
    LocalDateTime date;

    public Email(String from, String to, String title, String body, LocalDateTime date) {
        this.from = from;
        this.to = to;
        this.title = title;
        this.body = body;
        this.date = date;
    }
}
