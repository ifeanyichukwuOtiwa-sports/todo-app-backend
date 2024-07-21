package iwo.wintech.todoapp.service.auth;

import com.google.protobuf.InvalidProtocolBufferException;
import iwo.wintech.todoapp.api.dto.LoginResponse;
import iwo.wintech.todoapp.api.exception.ErrorCode;
import iwo.wintech.todoapp.api.service.AuthenticationService;
import iwo.wintech.todoapp.api.service.UserService;
import iwo.wintech.todoapp.api.user.ProtoUser;
import iwo.wintech.todoapp.persistence.dto.AuthenticateResponse;
import iwo.wintech.todoapp.persistence.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
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

        final byte[] existingTokenBytes = redisTemplate.opsForValue().get(userId);

        // if session is present Already invalidate and create a new one
        if (existingTokenBytes != null) {
            return new LoginResponse(new String(existingTokenBytes), user.getFirstName(), user.getLastName(), userId);
        }


        final String token = UUID.randomUUID().toString();
        final ProtoUser protoUser = mapToProto(user, token);

        redisTemplate.opsForValue().set(token, protoUser.toByteArray(), 3600, TimeUnit.SECONDS); // 1 hour expiry time
        redisTemplate.opsForValue().set(userId, token.getBytes(), 3599, TimeUnit.SECONDS);

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
                .addAllRoles(Set.of())
                .build();
    }

    @Override
    public AuthenticateResponse authenticate(final String token) {
        final byte[] redisUserSession = redisTemplate.opsForValue().get(token);

        if (redisUserSession == null) {
            throw ErrorCode.SESSION_EXPIRED.exception();
        }

        final ProtoUser protoUser = getParseFrom(redisUserSession);
        final User user = mapToUser(protoUser);

        return AuthenticateResponse.authenticated(user);
    }

    private User mapToUser(final ProtoUser protoUser) {
        return User.builder()
                .id(UUID.fromString(protoUser.getId()))
                .email(protoUser.getEmail())
                .firstName(protoUser.getFirstname())
                .lastName(protoUser.getLastname())
                .password(protoUser.getPassword())
                .createdAt(null) // create a new dto for this
                .build();
    }

    private static ProtoUser getParseFrom(final byte[] bytes) {
        try {
            return ProtoUser.parseFrom(bytes);
        } catch (InvalidProtocolBufferException e) {
            throw ErrorCode.SESSION_INVALID.exception();
        }
    }
}
