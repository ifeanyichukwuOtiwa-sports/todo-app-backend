package iwo.wintech.todoapp.persistence.api;

import iwo.wintech.todoapp.persistence.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByUsername(String username);

    User save(User user);

    List<User> findAll();

    Optional<User> findById(UUID uuid);
}
