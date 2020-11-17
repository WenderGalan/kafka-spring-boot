package io.github.wendergalan.producer.controllers;

import io.github.wendergalan.producer.producers.OrderProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderProducer orderProducer;

    @PostMapping
    public void send(@RequestBody String order) {
        // Chama o producer para enviar a ordem
        orderProducer.send(order);
    }
}
