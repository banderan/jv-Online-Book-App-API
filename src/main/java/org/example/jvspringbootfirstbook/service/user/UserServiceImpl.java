package org.example.jvspringbootfirstbook.service.user;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.example.jvspringbootfirstbook.dto.user.register.UserRegistrationRequestDto;
import org.example.jvspringbootfirstbook.dto.user.register.UserRegistrationResponseDto;
import org.example.jvspringbootfirstbook.exception.RegistrationException;
import org.example.jvspringbootfirstbook.mapper.UserMapper;
import org.example.jvspringbootfirstbook.model.Role;
import org.example.jvspringbootfirstbook.model.RoleName;
import org.example.jvspringbootfirstbook.model.User;
import org.example.jvspringbootfirstbook.repository.role.RoleRepository;
import org.example.jvspringbootfirstbook.repository.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final RoleName USER = RoleName.ROLE_USER;
    private static final Role role = new Role();
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserRegistrationResponseDto register(UserRegistrationRequestDto request) {
        checkIfUserExists(request.getEmail());
        User user = userMapper.toModel(request);
        Role getRole = roleRepository.findByName(USER).orElseGet(
                () -> roleRepository.save(role)
        );
        user.setRoles(Set.of(getRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    private void checkIfUserExists(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RegistrationException("User already exists");
        }
    }
}
