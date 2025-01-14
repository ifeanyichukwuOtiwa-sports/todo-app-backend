package iwo.wintech.todoapp.persistence.repository;

import iwo.wintech.todoapp.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<User, UUID> {

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByUsername(String username);
}
