package co.inventorsoft.springresttask.service.impl;

import co.inventorsoft.springresttask.controller.dto.EmailDto;
import co.inventorsoft.springresttask.service.EmailDeliveryService;
import co.inventorsoft.springresttask.service.mapper.EmailMapper;
import co.inventorsoft.springresttask.service.model.Email;
import co.inventorsoft.springresttask.service.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailDeliveryServiceImpl implements EmailDeliveryService {

    private final EmailMapper emailMapper;
    private final EmailRepository emailRepository;

    @Override
    public EmailDto getEmail(Integer id) {
        return emailMapper.mapModelToDto(emailRepository.getEmail(id));
    }

    @Override
    public List<EmailDto> listEmails() {
        return emailRepository.listEmails()
                .stream()
                .map(emailMapper::mapModelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmailDto createEmail(EmailDto emailDto) {
        log.info("Email with id {} was created", emailDto.getId());
        emailDto.setDate(LocalDateTime.now());
        Email email = emailRepository.createEmail(emailMapper.mapDtoToModel(emailDto));
        return emailMapper.mapModelToDto(email);
    }

    @Override
    public EmailDto updateEmail(Integer id, EmailDto emailDto) {
        Email savedEmail = emailRepository.getEmail(id);
        savedEmail.setDate(emailDto.getDate());
        log.info("Email with id {} was updated", emailDto.getId());
        return emailMapper.mapModelToDto(emailRepository.updateEmail(id, savedEmail));
    }

    @Override
    public void deleteEmail(Integer id) {
        emailRepository.deleteEmail(id);
        log.info("Email with id {} was deleted", id);
    }

}
