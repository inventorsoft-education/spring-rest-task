package co.inventorsoft.springresttask.service.impl;

import co.inventorsoft.springresttask.controller.dto.EmailDto;
import co.inventorsoft.springresttask.service.EmailDeliveryService;
import co.inventorsoft.springresttask.service.mapper.EmailMapper;
import co.inventorsoft.springresttask.service.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailDeliveryServiceImpl implements EmailDeliveryService {

    private final EmailMapper emailMapper;
    private final EmailRepository emailRepository;

    @Override
    public EmailDto getEmail(int id) {
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
        return emailMapper.mapModelToDto(emailRepository.createEmail(emailMapper.mapDtoToModel(emailDto)));
    }

    @Override
    public EmailDto updateEmail(int id, EmailDto emailDto) {
        emailDto.setId(id);
        log.info("Email with id {} was updated", emailDto.getId());
        return emailMapper.mapModelToDto(emailRepository.updateEmail(id, emailMapper.mapDtoToModel(emailDto)));
    }

    @Override
    public void deleteEmail(int id) {
        emailRepository.deleteEmail(id);
        log.info("Email with id {} was deleted", id);
    }

}
