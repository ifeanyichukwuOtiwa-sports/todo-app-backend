package iwo.wintech.todoapp.service.config;

import iwo.wintech.todoapp.security.config.TodoSecurityConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "iwo.wintech.todoapp.service")
@Import(TodoSecurityConfig.class)
public class TodoServiceConfig {
}
