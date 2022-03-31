package com.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
public class MailDto {
    private String sender;
    private String receiver;
    private String content;
    private String date;
}
