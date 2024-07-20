package iwo.wintech.todoapp.test.app.integration;

import iwo.wintech.todoapp.TodoAppApplication;
import iwo.wintech.todoapp.test.config.BasePersistenceTestBootstrapper;
import iwo.wintech.todoapp.test.app.integration.config.IntegrationTestConfig;
import iwo.wintech.todoapp.api.dto.CreateUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {IntegrationTestConfig.class, TodoAppApplication.class})
class TodoAppApplicationTests extends BasePersistenceTestBootstrapper {
    @Autowired
    TodoAppApplication application;

    @Test
    void contextLoads() {
        assertThat(application).isNotNull();
    }

    @Test
    void registerUser() {
        final CreateUserRequest requestBody = new CreateUserRequest("John", "Doe", "john.doe@gmail.com", "john.doe", "123456");

        final Boolean response = restClient.post()
                .uri("/api/auth/register")
                .body(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(Boolean.class);

        assertThat(response).isTrue();
    }
}
