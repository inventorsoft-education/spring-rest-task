package com.example.homework6;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Email;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Entity
@Table(name = "email")
public class EmailItem implements Comparable<EmailItem>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Email
    private String emailRecipient;

    @NotEmpty
    private String emailSubject;

    @NotEmpty
    private String emailBody;

    @NotEmpty
    private String deliveryDate;

    private boolean statusDelivery;

    public void setStatusDelivery(boolean statusDelivery) {
        this.statusDelivery = statusDelivery;
    }

    public boolean isStatusDelivery() {
        return statusDelivery;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getEmailBody() {
        return emailBody;
    }

    public String getEmailRecipient() {
        return emailRecipient;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }

    public void setDeliveryDate(String deliveryDate) {
       this.deliveryDate = deliveryDate;
    }

    public void setEmailRecipient(String emailRecipient) {
        this.emailRecipient = emailRecipient;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int compareTo(EmailItem o) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date1 = new Date();
        Date date2 = new Date();
        try {
            date1 = sdf.parse(this.getDeliveryDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            date2 = sdf.parse(o.getDeliveryDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date1.compareTo(date2);
    }
}
