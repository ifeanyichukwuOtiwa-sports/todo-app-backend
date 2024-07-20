package iwo.wintech.todoapp.redis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Data
@ConfigurationProperties(prefix = "app.redis")
public class RedisConnectionProperty {
    private String host = "localhost";
    private String port = "6379";
    private Duration connectionTimeout = Duration.ofMillis(10000);
    private Duration readTimeout = Duration.ofMillis(10000);
}
