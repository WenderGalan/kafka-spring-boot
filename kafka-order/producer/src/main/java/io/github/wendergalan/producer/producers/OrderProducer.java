package io.github.wendergalan.producer.producers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderProducer {
    private final KafkaTemplate kafkaTemplate;

    @Value("${order.topic}")
    private String orderTopic;

    public void send(final @RequestBody String order) {
        // Gera a key da mensagem aleatória
        final String mensagemKey = UUID.randomUUID().toString();
        // Faz o envio da mensagem para o kafka
        kafkaTemplate.send(orderTopic, mensagemKey, order);
    }
}
