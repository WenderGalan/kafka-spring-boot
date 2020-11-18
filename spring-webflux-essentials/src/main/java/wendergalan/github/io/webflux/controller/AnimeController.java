package wendergalan.github.io.webflux.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import wendergalan.github.io.webflux.domain.Anime;
import wendergalan.github.io.webflux.service.AnimeService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("animes")
@Slf4j
@RequiredArgsConstructor
@SecurityScheme(
        name = "Basic Authentication",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
public class AnimeController {
    private final AnimeService animeService;

    @GetMapping
    @ResponseStatus(OK)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            tags = {"anime"},
            security = @SecurityRequirement(name = "Basic Authentication")
    )
    public Flux<Anime> listAll() {
        return animeService.findAll();
    }

    @GetMapping("{id}")
    @ResponseStatus(OK)
    @Operation(
            tags = {"anime"},
            security = @SecurityRequirement(name = "Basic Authentication")
    )
    public Mono<Anime> findById(@PathVariable int id) {
        return animeService.findById(id)
                .switchIfEmpty(monoResponseStatusNotFoundException());
    }

    public <T> Mono<T> monoResponseStatusNotFoundException() {
        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found."));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(
            tags = {"anime"},
            security = @SecurityRequirement(name = "Basic Authentication")
    )
    public Mono<Anime> save(@Valid @RequestBody Anime anime) {
        return animeService.save(anime);
    }

    @PostMapping("batch")
    @ResponseStatus(CREATED)
    @Operation(
            tags = {"anime"},
            security = @SecurityRequirement(name = "Basic Authentication")
    )
    public Flux<Anime> saveBatch(@RequestBody List<Anime> animes) {
        return animeService.saveAll(animes);
    }

    @PutMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(
            tags = {"anime"},
            security = @SecurityRequirement(name = "Basic Authentication")
    )
    public Mono<Void> update(@PathVariable int id,
                             @Valid @RequestBody Anime anime) {
        return animeService.update(anime.withId(id));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(
            tags = {"anime"},
            security = @SecurityRequirement(name = "Basic Authentication")
    )
    public Mono<Void> delete(@PathVariable int id) {
        return animeService.delete(id);
    }
}
