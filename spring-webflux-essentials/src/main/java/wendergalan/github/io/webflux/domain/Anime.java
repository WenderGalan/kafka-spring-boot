package wendergalan.github.io.webflux.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@With
@Builder
@Table("anime")
public class Anime {
    @Id
    private Integer id;

    @NotNull
    @NotEmpty(message = "The name of this anime cannot is empty.")
    private String name;
}
