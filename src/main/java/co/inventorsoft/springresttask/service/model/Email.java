package co.inventorsoft.springresttask.service.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Email {

    private Integer id;
    private String sender;
    private String receiver;
    private String theme;
    private String content;
    private Boolean sent;
    private LocalDateTime date;

}
