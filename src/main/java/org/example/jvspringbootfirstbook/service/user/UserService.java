package org.example.jvspringbootfirstbook.service.user;

import org.example.jvspringbootfirstbook.dto.user.register.UserRegistrationRequestDto;
import org.example.jvspringbootfirstbook.dto.user.register.UserRegistrationResponseDto;

public interface UserService {
    UserRegistrationResponseDto register(UserRegistrationRequestDto request);
}
