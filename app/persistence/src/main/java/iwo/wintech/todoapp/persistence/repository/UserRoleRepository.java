package iwo.wintech.todoapp.persistence.repository;

import iwo.wintech.todoapp.persistence.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {
}
