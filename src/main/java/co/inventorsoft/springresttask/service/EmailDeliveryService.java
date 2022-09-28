package co.inventorsoft.springresttask.service;

import co.inventorsoft.springresttask.controller.dto.EmailDto;

import java.util.List;

public interface EmailDeliveryService {

    EmailDto getEmail(int id);

    List<EmailDto> listEmails();

    EmailDto createEmail(EmailDto emailDto);

    EmailDto updateEmail(int id, EmailDto emailDto);

    void deleteEmail(int id);

}
