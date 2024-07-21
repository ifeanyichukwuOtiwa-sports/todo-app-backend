package iwo.wintech.todoapp.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "role")
@Table(name = "role")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "role_id", columnDefinition = "BINARY(16)")
    private UUID roleId;

    @ManyToMany
    @JoinTable(

            name = "users_roles",
            inverseJoinColumns = @JoinColumn(
                    name = "user_uuid",
                    referencedColumnName = "id"
            ),
            joinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "role_id"
            )
    )
    private List<User> user;

    @Column(name = "role_name")
    private String roleName;
}
