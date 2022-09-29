package co.inventorsoft.springresttask.service;

import co.inventorsoft.springresttask.controller.dto.EmailDto;

import java.util.List;

public interface EmailDeliveryService {

    EmailDto getEmail(Integer id);

    List<EmailDto> listEmails();

    EmailDto createEmail(EmailDto emailDto);

    EmailDto updateEmail(Integer id, EmailDto emailDto);

    void deleteEmail(Integer id);

}
