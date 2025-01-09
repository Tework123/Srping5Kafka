package ru.tework.spring5micro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class KafkaEventConfig {
    //  регистрируем bean restTemplate для отправки hhtp запросов
    @Bean
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
