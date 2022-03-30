package com.inventor.email;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class Email implements Serializable {
    private String oderzhuvach;
    private String tema;
    private String tekst;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd.MM.yyyy HH:mm", timezone="EET")
    private Date dataOtrymannya;
    private Boolean nadislano;

    Email() {
        oderzhuvach = "victordoom@i.ua";
        tema = "Run";
        tekst = "Runs";
        dataOtrymannya = new Date();
        nadislano = false;
    }

    @Override
    public String toString() {
        return "\nОдержувач: " + oderzhuvach + "\nТема: " + tema + "\nТекст: " + tekst + "\nДата отримання: " + dataOtrymannya + "\nНадіслано: " + nadislano + "\n";
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null)
            return false;
        if(oderzhuvach.equals(((Email)obj).oderzhuvach)
                && tema.equals(((Email)obj).tema)
                && dataOtrymannya.equals(((Email)obj).dataOtrymannya)
                && tekst.equals(((Email)obj).tekst)) {
            return true;
        }
        return false;
    }

}
