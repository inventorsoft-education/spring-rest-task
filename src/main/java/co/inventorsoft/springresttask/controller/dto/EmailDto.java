package co.inventorsoft.springresttask.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class EmailDto {

    private int id;
    private String sender;
    private String receiver;
    private String theme;
    private String content;
    private boolean sent;
    private Timestamp date;

}
