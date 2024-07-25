package org.example.jvspringbootfirstbook.service.user;

import org.example.jvspringbootfirstbook.dto.UserRegistrationRequestDto;
import org.example.jvspringbootfirstbook.dto.UserResponseDto;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto request);
}
