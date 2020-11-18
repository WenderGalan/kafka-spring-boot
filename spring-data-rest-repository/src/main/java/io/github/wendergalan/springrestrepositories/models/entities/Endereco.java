package io.github.wendergalan.springrestrepositories.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * The type Endereco.
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private String logradouro;

    @Max(999999)
    @Min(1)
    private Integer numero;

    @NotNull
    private String cep;

    private String cidade;

    private String estado;

    // https://stackoverflow.com/questions/40634832/cascade-persist-with-spring-data-rest
    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "endereco")
    @RestResource(exported = false)
    private List<Pessoa> pessoas;
}
