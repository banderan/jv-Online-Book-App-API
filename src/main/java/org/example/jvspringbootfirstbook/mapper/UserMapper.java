package org.example.jvspringbootfirstbook.mapper;

import org.example.jvspringbootfirstbook.config.MapperConfig;
import org.example.jvspringbootfirstbook.dto.user.UserRegistrationRequestDto;
import org.example.jvspringbootfirstbook.dto.user.UserResponseDto;
import org.example.jvspringbootfirstbook.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    User toModel(UserRegistrationRequestDto registrationRequestDto);
}
