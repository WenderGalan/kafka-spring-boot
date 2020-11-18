package io.github.wendergalan.springrestrepositories.models.handlers;

import io.github.wendergalan.springrestrepositories.models.entities.Pessoa;
import lombok.extern.java.Log;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

@Log
@RepositoryEventHandler
public class PessoaEventHandler {

    @HandleBeforeSave
    @HandleBeforeCreate
    public void handlePessoaSaveOrCreate(Pessoa pessoa) {
        log.info("handlePessoaSaveOrCreate: " + (pessoa != null ? pessoa.toString() : "{}"));
    }
}
