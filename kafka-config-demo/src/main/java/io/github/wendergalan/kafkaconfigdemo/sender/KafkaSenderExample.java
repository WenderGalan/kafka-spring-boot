package io.github.wendergalan.kafkaconfigdemo.sender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaSenderExample {
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Apenas chama o metodo com a mensagem e o nome do topico
     *
     * @param message   {String}
     * @param topicName {String}
     */
    void sendMessage(String message, String topicName) {
        log.info("Sending : {}", message);
        log.info("---------------------------------");
        kafkaTemplate.send(topicName, message);
    }

    void sendMessageWithCallback(String message, String topicName) {
        log.info("Sending : {}", message);
        log.info("---------------------------------");
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);

        // Adicionado o callback do envio
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Message [{}] delivered with offset {}",
                        message,
                        result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.warn("Unable to deliver message [{}]. {}",
                        message,
                        ex.getMessage());
            }
        });
    }
}
