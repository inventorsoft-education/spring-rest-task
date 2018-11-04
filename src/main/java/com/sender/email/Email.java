package com.sender.email;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class Email implements Serializable {
    private String recipient;

    private String subject;

    private String body;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm", timezone="EET")
    private Date deliveryDate;

    private Boolean isSent;


    Email() {
        recipient = "A1lexen30@gmail.com";
        subject = "TEST!";
        body = "TEST";
        deliveryDate = new Date();
        isSent = false;
    }

    @Override
    public String toString() {
        return "\nRecipient: " + recipient + "\n Subject: " + subject + "\n Body: " + body + "\n Delivery date: " + deliveryDate + "\n isSent: " + isSent;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null)
            return false;
        if(recipient.equals(((Email)obj).recipient)
                && subject.equals(((Email)obj).subject)
                && deliveryDate.equals(((Email)obj).deliveryDate)) {
            return true;
        }
        return false;
    }

}
