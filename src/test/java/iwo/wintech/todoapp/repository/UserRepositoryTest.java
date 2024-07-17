package iwo.wintech.todoapp.repository;

import iwo.wintech.todoapp.config.PersistenceBootstrapper;
import iwo.wintech.todoapp.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class UserRepositoryTest extends PersistenceBootstrapper {

    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {
        assertThat(userRepository).isNotNull();
    }


    @Test
    void whenSaveUser_thenFindById() {
        final User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@gmail.com")
                .username("john.doe")
                .password("123456")
                .build();
        final User saved = userRepository.save(user);
        User foundUser = userRepository.findById(user.getId()).orElse(null);
        assertThat(saved).isNotNull()
                .isEqualTo(foundUser);
    }


}