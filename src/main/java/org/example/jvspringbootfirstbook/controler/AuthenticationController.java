package org.example.jvspringbootfirstbook.controler;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.jvspringbootfirstbook.dto.UserRegistrationRequestDto;
import org.example.jvspringbootfirstbook.dto.UserResponseDto;
import org.example.jvspringbootfirstbook.exception.RegistrationException;
import org.example.jvspringbootfirstbook.service.userServices.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;

    @PostMapping("/registration")
    public UserResponseDto register(
            @RequestBody @Valid UserRegistrationRequestDto request) throws RegistrationException {

        return userService.register(request);
    }
}
