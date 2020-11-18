package io.github.wendergalan.springrestrepositories.models.entities.projections;

import io.github.wendergalan.springrestrepositories.models.entities.Pessoa;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDate;

/**
 * https://docs.spring.io/spring-data/rest/docs/current/reference/html/#projections-excerpts.projections
 * The interface Sem endereco.
 */
@Projection(name = "semEndereco", types = {Pessoa.class})
public interface SemEndereco {

    @Value("#{target.id}")
    long getId();

    String getNome();

    String getTelefone();

    String getCpf();

    String getEmail();

    LocalDate getNascimento();

    // @Value("#{target.getIdade()}")
    // String getIdade();

}
