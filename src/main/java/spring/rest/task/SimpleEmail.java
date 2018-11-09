package spring.rest.task;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class SimpleEmail{
    @Email
    @NotNull
    private String to;
    @NotNull
    private String text;
    @NotNull
    private String subject;
    @NotNull
    private String date;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String toString() {
        return "to=" + to +
                ", text='" + text + '\'' +
                ", subject='" + subject + '\'' +
                ", date=" + date +
                '}';
    }
}
