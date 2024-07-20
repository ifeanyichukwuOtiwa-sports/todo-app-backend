package iwo.wintech.todoapp.persistence.repository;

import iwo.wintech.todoapp.persistence.api.UserRepository;
import iwo.wintech.todoapp.persistence.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
class UserRepositoryWrapper implements UserRepository {
    private final UserJpaRepository userJpaRepository;


    @Override
    public Optional<User> findUserByEmail(final String email) {
        return userJpaRepository.findUserByEmail(email);
    }

    @Override
    public Optional<User> findUserByUsername(final String username) {
        return userJpaRepository.findUserByUsername(username);
    }

    @Override
    public User save(final User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userJpaRepository.findAll();
    }

    @Override
    public Optional<User> findById(final UUID userUuid) {
        return userJpaRepository.findById(userUuid);
    }
}
