package io.github.wendergalan.kafkaconfigdemo.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Quando o ouvinte recebe mensagens, ele as converte nos tipos de destino e tenta
 * comparar esse tipo com as assinaturas de método para descobrir qual método chamar.
 * <p>
 * Sempre que não houver correspondência, o manipulador padrão (definido por isDefault=true) será chamado.
 */
//@Component
//@KafkaListener(id = "class-level", topics = "galan-2")
//@Slf4j
//public class KafkaClassListener {
//
//    @KafkaHandler
//    void listen(String message) {
//        log.info("KafkaHandler[String] {}", message);
//    }
//
//    @KafkaHandler(isDefault = true)
//    void listenDefault(Object object) {
//        log.info("KafkaHandler[Default] {}", object);
//    }
//}
