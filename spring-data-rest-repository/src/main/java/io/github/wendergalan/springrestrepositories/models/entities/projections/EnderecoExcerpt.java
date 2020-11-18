package io.github.wendergalan.springrestrepositories.models.entities.projections;

import io.github.wendergalan.springrestrepositories.models.entities.Endereco;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "enderecoExcerpt", types = {Endereco.class})
public interface EnderecoExcerpt {

    @Value("#{target.id}")
    long getId();

    String getLogradouro();

    Integer getNumero();

    String getCep();

    String getCidade();

    String getEstado();
}
