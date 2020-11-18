package io.github.wendergalan.kafkaconfigdemo.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

//@Component
@Slf4j
public class KafkaListenersExample {

    /**
     * Registra um listener para o topic informado
     *
     * @param data
     */
    @KafkaListener(topics = "galan-1")
    void listener(String data) {
        log.info(data);
    }

    /**
     * Registra um listener para os topics informados
     *
     * @param message
     */
    @KafkaListener(
            topics = "galan-1, galan-2",
            groupId = "galan-group-2")
    void commonListenerForMultipleTopics(String message) {
        log.info("MultipleTopicListener - {}", message);
    }

    /**
     * se quisermos receber todas as mensagens enviadas para um tópico desde o momento de sua
     * criação na inicialização do aplicativo, podemos definir o deslocamento inicial para zero
     *
     * @param message
     * @param partition
     * @param offset
     */
    @KafkaListener(
            groupId = "galan-group-3",
            topicPartitions = @TopicPartition(
                    topic = "galan-1",
                    partitionOffsets = {@PartitionOffset(
                            // Escutando a partição 0
                            partition = "0",
                            // Como especificamos initialOffset = "0", receberemos todas as mensagens
                            // a partir do deslocamento 0 toda vez que reiniciarmos o aplicativo.
                            initialOffset = "0")}))
    void listenToPartitionWithOffset(
            @Payload String message,
            // Também podemos recuperar alguns metadados úteis sobre a mensagem consumida usando a @Header() anotação.
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
            @Header(KafkaHeaders.OFFSET) int offset) {
        log.info("Received message [{}] from partition-{} with offset-{}",
                message,
                partition,
                offset);
    }

    /**
     * Spring permite enviar o valor de retorno do método para o destino especificado com @SendTo
     *
     * @param message
     * @return
     */
    @KafkaListener(topics = "galan-others")
    @SendTo("galan-1")
    String listenAndReply(String message) {
        log.info("ListenAndReply [{}]", message);
        return "This is a reply sent after receiving message";
    }
}
