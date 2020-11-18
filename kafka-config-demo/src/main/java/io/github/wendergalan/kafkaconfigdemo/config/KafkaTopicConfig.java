package io.github.wendergalan.kafkaconfigdemo.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    /**
     * Define os topicos para o kafka
     *
     * @return {NewTopic}
     */
    @Bean
    public NewTopic topicGalan1() {
        return TopicBuilder.name("galan-1").build();
    }

    /**
     * Define os topicos para o kafka
     *
     * @return {NewTopic}
     */
    @Bean
    public NewTopic topicGalan2() {
        return TopicBuilder.name("galan-2").build();
    }
}
