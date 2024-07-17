package iwo.wintech.todoapp.service;

import iwo.wintech.todoapp.exception.ErrorCode;
import iwo.wintech.todoapp.model.User;
import iwo.wintech.todoapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(final CreateUserRequest request) {
        final User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .build();
        return userRepository.save(user);
    }

    @Override
    public User findUserByEmailOrUsername(final String emailOrUsername) {
        return userRepository.findUserByEmail(emailOrUsername)
                .or(() -> userRepository.findUserByUsername(emailOrUsername))
                .orElseThrow(ErrorCode.USER_NOT_FOUND::exception);
    }

    @Override
    public User findUserById(final String userId) {
        return userRepository.findById(UUID.fromString(userId))
                .orElseThrow(ErrorCode.USER_NOT_FOUND::exception);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(u -> new UserResponse(u.getId().toString(),
                        u.getFirstName(),
                        u.getLastName(),u.getEmail(), u.getUsername(), u.getCreatedAt())
                )
                .toList();
    }
}
