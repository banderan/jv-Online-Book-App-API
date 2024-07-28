package org.example.jvspringbootfirstbook.service.user;

import org.example.jvspringbootfirstbook.dto.user.UserRegistrationRequestDto;
import org.example.jvspringbootfirstbook.dto.user.UserResponseDto;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto request);
}
