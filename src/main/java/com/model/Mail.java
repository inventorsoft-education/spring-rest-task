package com.model;

import com.dto.MailDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Mail {
    private int id;
    private String sender;
    private String receiver;
    private String content;
    private String date;

    public Mail(int id, MailDto mailDto){
        this.id=id;
        this.sender=mailDto.getSender();
        this.receiver= mailDto.getReceiver();
        this.content= mailDto.getContent();
        this.date=mailDto.getDate();
    }
}
