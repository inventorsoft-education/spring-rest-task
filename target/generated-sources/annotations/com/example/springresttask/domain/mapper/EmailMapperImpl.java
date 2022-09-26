package com.example.springresttask.domain.mapper;

import com.example.springresttask.domain.Email;
import com.example.springresttask.domain.dto.EmailDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-26T13:43:32+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 18.0.1.1 (Oracle Corporation)"
)
@Component
public class EmailMapperImpl implements EmailMapper {

    @Override
    public Email toEntity(EmailDto dto) {
        if ( dto == null ) {
            return null;
        }

        Email email = new Email();

        email.setRecipientName( dto.getRecipientName() );
        email.setEmailSubject( dto.getEmailSubject() );
        email.setEmailBody( dto.getEmailBody() );
        email.setIsSent( dto.getIsSent() );

        return email;
    }

    @Override
    public EmailDto toDto(Email email) {
        if ( email == null ) {
            return null;
        }

        EmailDto emailDto = new EmailDto();

        emailDto.setRecipientName( email.getRecipientName() );
        emailDto.setEmailSubject( email.getEmailSubject() );
        emailDto.setEmailBody( email.getEmailBody() );
        emailDto.setIsSent( email.getIsSent() );

        return emailDto;
    }

    @Override
    public void update(EmailDto dto, Email entity) {
        if ( dto == null ) {
            return;
        }

        entity.setRecipientName( dto.getRecipientName() );
        entity.setEmailSubject( dto.getEmailSubject() );
        entity.setEmailBody( dto.getEmailBody() );
        entity.setIsSent( dto.getIsSent() );
    }
}
