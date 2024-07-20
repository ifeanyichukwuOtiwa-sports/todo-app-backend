package iwo.wintech.todoapp.service.user;

import iwo.wintech.todoapp.api.dto.CreateUserRequest;
import iwo.wintech.todoapp.api.dto.UserResponse;
import iwo.wintech.todoapp.api.service.UserService;
import iwo.wintech.todoapp.api.exception.ErrorCode;
import iwo.wintech.todoapp.persistence.api.UserRepository;
import iwo.wintech.todoapp.persistence.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean createUser(final CreateUserRequest request) {

        final User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .build();
        try {
            return Objects.nonNull(userRepository.save(user));
        } catch (Exception e) {
            log.error("problem encountered when creating user", e);
            return false;
        }

    }

    @Override
    public UserResponse findUserByEmailOrUsername(final String emailOrUsername) {
        return findUserByCreds(emailOrUsername)
                .map(this::mapToUserResponse )
                .orElseThrow(ErrorCode.USER_NOT_FOUND::exception);
    }

    private Optional<User> findUserByCreds(final String emailOrUsername) {
        return userRepository.findUserByEmail(emailOrUsername)
                .or(() -> userRepository.findUserByUsername(emailOrUsername));
    }

    @Override
    public UserResponse findUserById(final String userId) {
        return userRepository.findById(UUID.fromString(userId))
                .map(this::mapToUserResponse)
                .orElseThrow(ErrorCode.USER_NOT_FOUND::exception);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToUserResponse
                )
                .toList();
    }

    @Override
    public User findUser(final String username) {
        return findUserByCreds(username).orElseThrow(ErrorCode.USER_NOT_FOUND::exception);
    }

    private UserResponse mapToUserResponse(final User u) {
        return new UserResponse(u.getId().toString(),
                u.getFirstName(),
                u.getLastName(), u.getEmail(), u.getUsername(), u.getCreatedAt());
    }
}
