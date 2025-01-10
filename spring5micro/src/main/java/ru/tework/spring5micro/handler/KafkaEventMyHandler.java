package ru.tework.spring5micro.handler;

import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import ru.tework.spring5micro.entities.Task;
import ru.tework.spring5micro.exception.NonRetryableException;
import ru.tework.spring5micro.exception.RetryableException;
import ru.tework.spring5micro.repositories.TaskRepository;
import ru.tework.spring6kafka.KafkaEventMy;

@Component
@KafkaListener(topics = "product-created-events-topic")
public class KafkaEventMyHandler {

    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private RestTemplate restTemplate;
    private TaskRepository taskRepository;

    public KafkaEventMyHandler(RestTemplate restTemplate, TaskRepository taskRepository) {
        this.restTemplate = restTemplate;
        this.taskRepository = taskRepository;
    }

    // эта штука ловит сообщения
    @Transactional
    @KafkaHandler
    public void handle(@Payload KafkaEventMy kafkaEventMy,
            @Header("messageId") String messageId,
            @Header(KafkaHeaders.RECEIVED_KEY) String messageKey) {

        LOGGER.info("GET SOMETHING");

        Task task = taskRepository.findByMessageId(messageId);
        if (task != null) {
            LOGGER.info("DUPLICATE MessageId: {}", messageId);
            return;
        }

        String url = "http://localhost:8080/student";

        try {
            ResponseEntity<String> response = restTemplate.exchange(url,
                    HttpMethod.GET, null, String.class);
            if (response.getStatusCode().value() == HttpStatus.OK.value()) {
                LOGGER.info("SuCEESSS {}", response.getBody());

            }
        } catch (ResourceAccessException e) {
            LOGGER.error(e.getMessage());
            throw new RetryableException(e);
        } catch (HttpServerErrorException e) {
            LOGGER.error(e.getMessage());
            throw new NonRetryableException(e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new NonRetryableException(e);
        }

        try {
            taskRepository.save(new Task(messageId, kafkaEventMy.getTitle()));
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.getMessage());
            throw new NonRetryableException(e);
        }
    }

}
