package co.inventorsoft.springresttask.controller;


import co.inventorsoft.springresttask.controller.dto.EmailDto;
import co.inventorsoft.springresttask.service.EmailDeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
@Slf4j
public class EmailDeliveryController {

    private final EmailDeliveryService emailDeliveryService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<EmailDto> getAllEmails() {
        log.info("getAllEmails");
        return emailDeliveryService.listEmails();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public EmailDto getEmailById(@PathVariable int id) {
        log.info("getEmail by id {}", id);
        return emailDeliveryService.getEmail(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public EmailDto createEmail(@RequestBody EmailDto emailDto) {
        log.info("createEmail with id {}", emailDto.getId());
        return emailDeliveryService.createEmail(emailDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public EmailDto updateEmail(@PathVariable int id, @RequestBody EmailDto emailDto) {
        log.info("updateEmail by id {}", id);
        return emailDeliveryService.updateEmail(id, emailDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        log.info("deleteEmail by id {}", id);
        emailDeliveryService.deleteEmail(id);
    }

}
