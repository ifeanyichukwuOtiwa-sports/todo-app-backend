package iwo.wintech.todoapp.persistence.repository;

import iwo.wintech.todoapp.persistence.config.TodoPersistenceConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestClient;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@Slf4j
@ContextConfiguration(classes = TodoPersistenceConfig.class)
@DataJpaTest
public class PersistenceBootstrapper {

    @Container
    private static final MySQLContainer<?> MYSQLCONTAINER = new MySQLContainer<>(DockerImageName.parse("mysql:8.4.0"))
            .withPassword("123456")
            .withUsername("root")
            .withDatabaseName("users_mysql");

    static {
        MYSQLCONTAINER.start();
    }


    @DynamicPropertySource
    public static void redisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MYSQLCONTAINER::getJdbcUrl);
        registry.add("spring.datasource.password", MYSQLCONTAINER::getPassword);
        registry.add("spring.datasource.username", MYSQLCONTAINER::getUsername);
    }
}
