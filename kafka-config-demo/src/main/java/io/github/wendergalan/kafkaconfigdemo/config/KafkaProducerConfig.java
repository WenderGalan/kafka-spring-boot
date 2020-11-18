package io.github.wendergalan.kafkaconfigdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * Configura o producer kafka para poder enviar mensagens.
 */
@Configuration
@Slf4j
public class KafkaProducerConfig {

    @Value("${io.github.wendergalan.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        // Host e porta em que o kafka está sendo executado.
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // Classe do serializador a ser usado para a chave
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // Classe do serializador a ser usado para o valor
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }

    /**
     * ProducerFactory é responsável para enviar mensagens.
     *
     * @return {ProducerFactory}
     */
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    /**
     * KafkaTemplate nos ajuda a enviar mensagens para os respectivos tópicos.
     *
     * @return {KafkaTemplate}
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactory());
//        kafkaTemplate.setMessageConverter(new StringJsonMessageConverter());
//        kafkaTemplate.setDefaultTopic("galan-user");
//        // Registra um ProcucerListener
//        kafkaTemplate.setProducerListener(new ProducerListener<String, String>() {
//            @Override
//            public void onSuccess(ProducerRecord<String, String> producerRecord, RecordMetadata recordMetadata) {
//                log.info("ACK from ProducerListener message: {} offset:  {}", producerRecord.value(), recordMetadata.offset());
//            }
//
//            @Override
//            public void onError(ProducerRecord<String, String> producerRecord, Exception exception) {
//                // onError
//            }
//        });
        return kafkaTemplate;
    }
}
