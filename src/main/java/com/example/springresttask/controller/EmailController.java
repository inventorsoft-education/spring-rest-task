package com.example.springresttask.controller;

import com.example.springresttask.domain.Email;
import com.example.springresttask.domain.dto.EmailDto;
import com.example.springresttask.domain.mapper.EmailMapper;
import com.example.springresttask.service.EmailService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor

public class EmailController {

    private final EmailService emailService;
    private final EmailMapper emailMapper;


    @GetMapping
    public List<EmailDto> getAllPendingEmailDeliveries() {
        return emailService.pendingEmailDeliveries().stream()
                .map(emailMapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Email addNewEmailDelivery(@RequestBody EmailDto emailDto) {
        return emailService.createEmailDelivery(emailMapper.toEntity(emailDto));
    }

    @PatchMapping("/{id}")
    public String updateDeliveryDate(@PathVariable Long id,
                                     @RequestBody @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime deliveryDate) {
        return emailService.updateDeliveryDate(id, deliveryDate) + "Time update";

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePendingEmail(@PathVariable Long id) {
        emailService.removePendingEmail(id);
    }
}
