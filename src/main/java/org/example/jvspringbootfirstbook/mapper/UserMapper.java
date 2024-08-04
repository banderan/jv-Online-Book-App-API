package org.example.jvspringbootfirstbook.mapper;

import org.example.jvspringbootfirstbook.config.MapperConfig;
import org.example.jvspringbootfirstbook.dto.user.register.UserRegistrationRequestDto;
import org.example.jvspringbootfirstbook.dto.user.register.UserRegistrationResponseDto;
import org.example.jvspringbootfirstbook.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserRegistrationResponseDto toDto(User user);

    User toEntity(UserRegistrationRequestDto registrationRequestDto);
}
