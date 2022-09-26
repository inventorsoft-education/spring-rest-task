package com.example.springresttask.domain.mapper;

import com.example.springresttask.domain.Email;
import com.example.springresttask.domain.dto.EmailDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmailMapper {


    Email toEntity(EmailDto dto);

    EmailDto toDto(Email email);

    void update(EmailDto dto, @MappingTarget Email entity);
}
