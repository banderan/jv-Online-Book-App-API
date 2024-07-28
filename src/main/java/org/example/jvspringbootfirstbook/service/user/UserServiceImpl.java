package org.example.jvspringbootfirstbook.service.user;

import lombok.RequiredArgsConstructor;
import org.example.jvspringbootfirstbook.dto.UserRegistrationRequestDto;
import org.example.jvspringbootfirstbook.dto.UserResponseDto;
import org.example.jvspringbootfirstbook.exception.RegistrationException;
import org.example.jvspringbootfirstbook.mapper.UserMapper;
import org.example.jvspringbootfirstbook.model.User;
import org.example.jvspringbootfirstbook.repository.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto request) {
        checkIfUserExists(request.getEmail());
        User user = userMapper.toModel(request);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    private void checkIfUserExists(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RegistrationException("User already exists");
        }
    }
}
