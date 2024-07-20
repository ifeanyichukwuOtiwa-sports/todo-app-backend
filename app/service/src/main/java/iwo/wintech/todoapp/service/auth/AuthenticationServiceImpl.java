package iwo.wintech.todoapp.service.auth;

import iwo.wintech.todoapp.api.dto.LoginResponse;
import iwo.wintech.todoapp.api.exception.ErrorCode;
import iwo.wintech.todoapp.api.service.AuthenticationService;
import iwo.wintech.todoapp.api.service.UserService;
import iwo.wintech.todoapp.api.user.ProtoUser;
import iwo.wintech.todoapp.persistence.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, byte[]> redisTemplate;


    public LoginResponse login(final String username, final String password) {
        final User user = userService.findUser(username);
        final boolean matches = passwordEncoder.matches(password, user.getPassword());

        if (!matches) {
            throw ErrorCode.WRONG_PASSWORD.exception();
        }

        final String userId = user.getId().toString();

        final byte[] bytes = redisTemplate.opsForValue().get(userId);
        // if session is present Already invalidate and create a new one
        Optional.ofNullable(bytes)
                .ifPresent(ignored -> redisTemplate.delete(userId));

        final String token = UUID.randomUUID().toString();
        final ProtoUser protoUser = mapToProto(user, token);

        redisTemplate.opsForValue().set(token, protoUser.toByteArray(), 3600, TimeUnit.SECONDS); // 1 hour expiry time
        return new LoginResponse(token, user.getFirstName(), user.getLastName(), userId);
    }

    private ProtoUser mapToProto(final User user, final String token) {
        return ProtoUser.newBuilder()
                .setEmail(user.getEmail())
                .setToken(token)
                .setId(user.getId().toString())
                .setFirstname(user.getFirstName())
                .setLastname(user.getLastName())
                .setPassword(user.getPassword())
                .build();
    }
}
