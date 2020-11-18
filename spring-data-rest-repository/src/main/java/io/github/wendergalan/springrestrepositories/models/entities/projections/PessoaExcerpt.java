package io.github.wendergalan.springrestrepositories.models.entities.projections;

import io.github.wendergalan.springrestrepositories.models.entities.Endereco;
import io.github.wendergalan.springrestrepositories.models.entities.Pessoa;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDate;

@Projection(name = "pessoaExcerpt", types = {Pessoa.class})
public interface PessoaExcerpt {

    @Value("#{target.id}")
    long getId();

    String getNome();

    String getTelefone();

    String getCpf();

    String getEmail();

    LocalDate getNascimento();

//    @Value("#{target.getIdade()}")
//    String getIdade();
}
