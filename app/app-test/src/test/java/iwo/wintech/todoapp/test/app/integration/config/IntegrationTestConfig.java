package iwo.wintech.todoapp.test.app.integration.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.client.RestClient;

@TestConfiguration(proxyBeanMethods = false)
public class IntegrationTestConfig {


    @Lazy
    @Bean
    RestClient restClient(@Value("${local.server.port}") final String port) {
        System.out.println(port);
        return RestClient.builder()
                .baseUrl("http://localhost:%s".formatted(port))
                .build();
    }
}
