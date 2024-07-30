package org.example.jvspringbootfirstbook.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.jvspringbootfirstbook.dto.user.login.UserLoginRequestDto;
import org.example.jvspringbootfirstbook.dto.user.login.UserLoginResponseDto;
import org.example.jvspringbootfirstbook.dto.user.register.UserRegistrationRequestDto;
import org.example.jvspringbootfirstbook.dto.user.register.UserRegistrationResponseDto;
import org.example.jvspringbootfirstbook.exception.RegistrationException;
import org.example.jvspringbootfirstbook.security.token.AuthenticationService;
import org.example.jvspringbootfirstbook.service.user.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    public UserRegistrationResponseDto register(
            @RequestBody @Valid UserRegistrationRequestDto request) throws RegistrationException {
        return userService.register(request);
    }

    @PostMapping("/login")
    public UserLoginResponseDto login(
            @RequestBody UserLoginRequestDto loginRequestDto) {
        return authenticationService.authenticate(loginRequestDto);
    }
}
