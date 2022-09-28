package co.inventorsoft.springresttask.service.mapper;

import co.inventorsoft.springresttask.controller.dto.EmailDto;
import co.inventorsoft.springresttask.service.model.Email;
import org.mapstruct.Mapper;

@Mapper
public interface EmailMapper {

    EmailDto mapModelToDto(Email email);

    Email mapDtoToModel(EmailDto emailDto);

}
