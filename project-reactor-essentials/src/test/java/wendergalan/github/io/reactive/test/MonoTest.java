package wendergalan.github.io.reactive.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
/*
  Reactive Streams
  1. Asynchronous
  2. Non-blocking
  3. Backpressure
  Publisher <- (subscribe) Subscriber
  Subscription é criado
  Publisher (onSubscribe with the subscription) -> Subscriber
  Subscription <- (request N) Subscriber
  Publisher -> (onNext) Subscriber
  Será executado até:
  1. Publisher enviar todos os objetos da requisição.
  2. Publisher enviar todos os objetos possíveis. (onComplete) subscriber and subscription serão cancelados.
  3. Quando tem algum tipo de erro. (onError) -> subscriber and subscription serão cancelados
 */
public class MonoTest {

    @Test
    public void monoSubscriber() {
        String name = "Wender";
        // Agora o mono é um publisher
        Mono<String> mono = Mono.just(name).log();

        // Faz a inscrição
        mono.subscribe();

        log.info("-------------------------------------------");

        StepVerifier.create(mono)
                .expectNext(name)
                .verifyComplete();
    }

    @Test
    public void monoSubscriberConsumer() {
        String name = "Wender";
        // Agora o mono é um publisher
        Mono<String> mono = Mono.just(name).log();

        // Faz a inscrição
        mono.subscribe(s -> log.info("Value {}", s));

        log.info("-------------------------------------------");

        StepVerifier.create(mono)
                .expectNext(name)
                .verifyComplete();
    }

    @Test
    public void monoSubscriberConsumerError() {
        String name = "Wender";
        // Agora o mono é um publisher
        Mono<String> mono = Mono.just(name)
                .map(s -> {
                    throw new RuntimeException("Testing mono with error");
                });

        // Faz a inscrição
        mono.subscribe(s -> log.info("Name {}", s), s -> log.error("Someting bad happened"));
        mono.subscribe(s -> log.info("Name {}", s), Throwable::printStackTrace);

        log.info("-------------------------------------------");

        StepVerifier.create(mono)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    public void monoSubscriberConsumerComplete() {
        String name = "Wender";
        // Agora o mono é um publisher
        Mono<String> mono = Mono.just(name)
                .log()
                .map(String::toUpperCase);

        // Faz a inscrição
        mono.subscribe(s -> log.info("Value {}", s),
                Throwable::printStackTrace,
                () -> log.info("FINISHED!"));

        log.info("-------------------------------------------");

        StepVerifier.create(mono)
                .expectNext(name.toUpperCase())
                .verifyComplete();
    }

    @Test
    public void monoSubscriberConsumerSubscription() {
        String name = "Wender";
        // Agora o mono é um publisher
        Mono<String> mono = Mono.just(name)
                .log()
                .map(String::toUpperCase);

        // Faz a inscrição
        mono.subscribe(s -> log.info("Value {}", s),
                Throwable::printStackTrace,
                () -> log.info("FINISHED!"),
                // Solicita apenas 5 itens
                subscription -> subscription.request(5));

        log.info("-------------------------------------------");

        StepVerifier.create(mono)
                .expectNext(name.toUpperCase())
                .verifyComplete();
    }

    @Test
    public void monoDoOnMethods() {
        String name = "Wender";
        // Agora o mono é um publisher
        Mono<Object> mono = Mono.just(name)
                .log()
                .map(String::toUpperCase)
                .doOnSubscribe(subscription -> log.info("Subscribed"))
                .doOnRequest(longNumber -> log.info("Request Received, stating doing something..."))
                // Todas as vezes que o publisher emitir algo
                .doOnNext(s -> log.info("Value is here. Executing doOnNext {}", s))
                // Limpa o mono
                .flatMap(s -> Mono.empty())
                // Essa linha não será executada
                .doOnNext(s -> log.info("Value is here. Executing doOnNext {}", s))
                .doOnSuccess(s -> log.info("doOnSuccess executed"));

        // Faz a inscrição
        mono.subscribe(s -> log.info("Value {}", s),
                Throwable::printStackTrace,
                () -> log.info("FINISHED!"));

        log.info("-------------------------------------------");

//        StepVerifier.create(mono)
//                .expectNext(name.toUpperCase())
//                .verifyComplete();
    }
}
