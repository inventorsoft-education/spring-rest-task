package co.inventorsoft.springresttask.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EmailDto {

    private Integer id;
    private String sender;
    private String receiver;
    private String theme;
    private String content;
    private Boolean sent;
    private LocalDateTime date;

}
