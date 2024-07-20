package iwo.wintech.todoapp.redis.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@ComponentScan(basePackages = "iwo.wintech.todoapp.redis")
@EnableConfigurationProperties(RedisConnectionProperty.class)
@Configuration
public class TodoRedisConfig {

    @Bean
    public JedisClientConfiguration jedisClientConfiguration(final RedisConnectionProperty prop) {
        return JedisClientConfiguration.builder()
                .connectTimeout(prop.getConnectionTimeout())
                .readTimeout(prop.getReadTimeout())
                .build();
    }

    @Bean
    public RedisStandaloneConfiguration redisConfig(final RedisConnectionProperty prop) {
        return new RedisStandaloneConfiguration(prop.getHost(), Integer.parseInt(prop.getPort()));
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(final RedisStandaloneConfiguration config,
                                                         final JedisClientConfiguration clientConfig) {
        return new JedisConnectionFactory(config, clientConfig);
    }

    @Bean
    public RedisTemplate<String, byte[]> redisTemplate(final RedisConnectionFactory conFact) {
        final RedisTemplate<String, byte[]> template = new RedisTemplate<>();
        template.setConnectionFactory(conFact);
        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(RedisSerializer.byteArray());
        return template;
    }

}
