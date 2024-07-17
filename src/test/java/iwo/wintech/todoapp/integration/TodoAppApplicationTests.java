package iwo.wintech.todoapp.integration;

import iwo.wintech.todoapp.TodoAppApplication;
import iwo.wintech.todoapp.config.PersistenceBootstrapper;
import iwo.wintech.todoapp.integration.config.IntegrationTestConfig;
import iwo.wintech.todoapp.service.CreateUserRequest;
import iwo.wintech.todoapp.service.UserResponse;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.assertj.core.api.InstanceOfAssertFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestClient;

import java.util.Base64;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {IntegrationTestConfig.class, TodoAppApplication.class})
class TodoAppApplicationTests extends PersistenceBootstrapper {
    @Autowired
    TodoAppApplication application;

    @Test
    void contextLoads() {
        assertThat(application).isNotNull();
    }

    @Test
    void registerUser() {
//        final String credentials = Base64.getEncoder().encodeToString("user:password".getBytes());
        final CreateUserRequest requestBody = new CreateUserRequest("John", "Doe", "john.doe@gmail.com", "john.doe", "123456");

        final Boolean response = restClient.post()
                .uri("/api/auth/register")
                .body(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(Boolean.class);

        assertThat(response).isTrue();

//        final List<UserResponse> usersInDb = restClient.get()
//                .uri("/api/admin/users")
//                .accept(MediaType.APPLICATION_JSON)
//                .header("Authorizetion", "Basic %s".formatted(credentials))
//                .retrieve()
//                .body(new ParameterizedTypeReference<List<UserResponse>>() {
//                });

//        assertThat(usersInDb).isNotNull()
//                .asInstanceOf(InstanceOfAssertFactories.LIST)
//                .first().usingRecursiveComparison()
//                .ignoringFields("userId", "createdAt")
//                .isEqualTo(new UserResponse(null, "John", "Doe", "john.doe@gmail.com", "john.doe", null));

    }
}
