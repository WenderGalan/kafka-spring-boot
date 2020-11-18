package io.github.wendergalan.springrestrepositories.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.Period;

/**
 * The type Pessoa.
 */
@Log
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(nullable = false)
    @Length(min = 0, max = 100)
    private String nome;

    @Length(max = 15)
    private String telefone;

    @CPF
    private String cpf;

    @Email
    private String email;

    private LocalDate nascimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_endereco")
    @RestResource(exported = false)
    private Endereco endereco;

    @PrePersist
    @PreUpdate
    private void preSaveOrUpdate() {
        log.info("preSaveOrUpdate: Faça algo antes de salvar ou atualizar.");
    }

    /**
     * Ainda não é possível utilizar métodos para retornar determinados valores, ocorre uma exception a inicializar a aplicação:
     * java.lang.IllegalStateException: Ambiguous models equality when conditions is empty.
     * Problem: https://github.com/springfox/springfox/issues/2345
     * https://github.com/springfox/springfox/pull/3132
     *
     * @return String
     */
//    public String getIdade() {
//        if (nascimento != null) {
//            LocalDate hoje = LocalDate.now();
//            Period dif = nascimento.until(hoje);
//
//            return String.format("%02d", dif.getYears()) + "A " + String.format("%02d", dif.getMonths()) + "M " + String.format("%02d", dif.getDays()) + "D";
//        } else return null;
//    }
}
