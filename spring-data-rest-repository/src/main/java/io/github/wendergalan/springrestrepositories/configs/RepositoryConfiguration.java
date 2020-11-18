package io.github.wendergalan.springrestrepositories.configs;

import io.github.wendergalan.springrestrepositories.models.handlers.PessoaEventHandler;
import io.github.wendergalan.springrestrepositories.models.listeners.EntityEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfiguration {

    public RepositoryConfiguration() {
        super();
    }

    /**
     * Esse bean serve para criar uma instância dos listeners.
     * <p>
     * See <a href="https://docs.spring.io/spring-data/rest/docs/current/reference/html/#events">Eventos</a>
     *
     * @return EntityEventListener entity event listener
     */
    @Bean
    EntityEventListener eventListenerEntities() {
        return new EntityEventListener();
    }

    /**
     * Pode ser definido handlers especificos de uma classe
     *
     * @return PessoaEventHandler
     */
    @Bean
    PessoaEventHandler pessoaEventHandler() {
        return new PessoaEventHandler();
    }
}
