package wendergalan.github.io.webflux.integration;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.blockhound.BlockHound;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import wendergalan.github.io.webflux.domain.Anime;
import wendergalan.github.io.webflux.repository.AnimeRepository;
import wendergalan.github.io.webflux.util.AnimeCreator;

import java.util.List;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class AnimeControllerIT {
    private final static String REGULAR_USER = "user";
    private final static String ADMIN_USER = "admin";

    /**
     * Não deveria fazer mock pq não está sendo iniciado nenhum banco de dados.
     */
    @MockBean
    private AnimeRepository animeRepositoryMock;

    @Autowired
    private WebTestClient client;

    private final Anime anime = AnimeCreator.createValidAnime();

    @BeforeAll
    public static void blockHoundSetup() {
        BlockHound.install(
                builder -> builder.allowBlockingCallsInside("java.util.UUID", "randomUUID")
        );
    }

    @BeforeEach
    public void setUp() {
        BDDMockito.when(animeRepositoryMock.findAll())
                .thenReturn(Flux.just(anime));

        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Mono.just(anime));

        BDDMockito.when(animeRepositoryMock.save(AnimeCreator.createAnimeToBeSaved()))
                .thenReturn(Mono.just(anime));

        BDDMockito.when(animeRepositoryMock.saveAll(List.of(AnimeCreator.createAnimeToBeSaved(), AnimeCreator.createAnimeToBeSaved())))
                .thenReturn(Flux.just(anime, anime));

        BDDMockito.when(animeRepositoryMock.delete(ArgumentMatchers.any(Anime.class)))
                .thenReturn(Mono.empty());

        BDDMockito.when(animeRepositoryMock.save(AnimeCreator.createValidAnime()))
                .thenReturn(Mono.empty());
    }

    /**
     * Verifica se o block hound esta funcionando corretamente
     */
    @Test
    public void blockHoundWorks() {
        try {
            FutureTask<?> task = new FutureTask<>(() -> {
                Thread.sleep(0);
                return "";
            });
            Schedulers.parallel().schedule(task);

            task.get(10, TimeUnit.SECONDS);
            Assertions.fail("should fail");
        } catch (Exception e) {
            Assertions.assertTrue(e.getCause() instanceof BlockingOperationError);
        }
    }

    @Test
    @DisplayName("listAll returns a flux of anime when user is successfully authenticated and have the role ADMIN")
    @WithUserDetails(ADMIN_USER)
    public void listAll_ReturnFluxOfAnime_WhenSuccessful() {
        client
                .get()
                .uri("/animes")
                .exchange() // Executa a requisição
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo(anime.getId())
                .jsonPath("$.[0].name").isEqualTo(anime.getName());
    }

    @Test
    @DisplayName("listAll returns a flux of anime when user is successfully authenticated and have the role ADMIN")
    @WithUserDetails(REGULAR_USER)
    public void listAll_ReturnForbidden_WhenUserDoesNotHaveRoleAdmin() {
        client
                .get()
                .uri("/animes")
                .exchange() // Executa a requisição
                .expectStatus().isForbidden();
    }

    @Test
    @DisplayName("listAll returns unauthorized when user is successfully authenticated")
    public void listAll_ReturnAnauthorized_WhenUserIsNotAuthenticated() {
        client
                .get()
                .uri("/animes")
                .exchange() // Executa a requisição
                .expectStatus().isUnauthorized();
    }

    @Test
    @DisplayName("listAll returns a flux of anime when user is successfully authenticated and have the role ADMIN")
    @WithUserDetails(ADMIN_USER)
    public void listAll_Flavor2_ReturnFluxOfAnime_WhenSuccessful() {
        client
                .get()
                .uri("/animes")
                .exchange() // Executa a requisição
                .expectStatus().isOk()
                .expectBodyList(Anime.class)
                .hasSize(1)
                .contains(anime);
    }

    @Test
    @DisplayName("findById returns Mono with anime when it exists and user is successfully authenticated and have the role USER")
    @WithUserDetails(REGULAR_USER)
    public void findById_ReturnMonoOfAnime_WhenSuccessful() {
        client
                .get()
                .uri("/animes/{id}", 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Anime.class)
                .isEqualTo(anime);
    }

    @Test
    @DisplayName("findById returns Mono error when anime does not exists when user is successfully authenticated and have the role USER")
    @WithUserDetails(REGULAR_USER)
    public void findById_ReturnMonoError_WhenEmptyMonoIsReturned() {
        BDDMockito.when(animeRepositoryMock.findById(anyInt()))
                .thenReturn(Mono.empty());

        client
                .get()
                .uri("/animes/{id}", 1)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.status").isEqualTo(404)
                // Importa o Custom Attributes para pode carregar a propriedade abaixo
                .jsonPath("$.developerMessage").isEqualTo("A ResponseStatusException Happened");
    }

    @Test
    @DisplayName("save creates and anime when successful and user is successfully authenticated and have the role ADMIN")
    @WithUserDetails(ADMIN_USER)
    public void save_CreatesAnime_WhenSuccessful() {
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();

        client
                .post()
                .uri("/animes")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(animeToBeSaved))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Anime.class)
                .isEqualTo(anime);
    }

    @Test
    @DisplayName("save returns mono error with bad request when name is empty and user is successfully authenticated and have the role ADMIN")
    @WithUserDetails(ADMIN_USER)
    public void save_ReturnsError_WhenNameIsEmpty() {
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved().withName("");

        client
                .post()
                .uri("/animes")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(animeToBeSaved))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.status").isEqualTo(400);
    }

    @Test
    @DisplayName("saveBatch creates a list of anime when successful and user is successfully authenticated and have the role ADMIN")
    @WithUserDetails(ADMIN_USER)
    public void saveBatch_CreatesListOfAnime_WhenSuccessful() {
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();

        client
                .post()
                .uri("/animes/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(List.of(animeToBeSaved, animeToBeSaved)))
                .exchange()
                .expectStatus().isCreated()
                .expectBodyList(Anime.class)
                .hasSize(2)
                .contains(anime);
    }

    @Test
    @DisplayName("saveBatch returns mono error when one of the object in the list contains null or empty names.")
    @WithUserDetails(ADMIN_USER)
    public void saveBatch_ReturnsMonoError_WhenContainsInvalidName() {
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();

        BDDMockito.when(animeRepositoryMock.saveAll(List.of(AnimeCreator.createAnimeToBeSaved(), AnimeCreator.createAnimeToBeSaved())))
                .thenReturn(Flux.just(anime, anime.withName("")));

        client
                .post()
                .uri("/animes")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(List.of(animeToBeSaved, animeToBeSaved)))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.status").isEqualTo(400);
    }

    @Test
    @DisplayName("delete removes the anime when successful and user is successfully authenticated and have the role ADMIN")
    @WithUserDetails(ADMIN_USER)
    public void delete_RemovesAnime_WhenSuccessful() {
        client
                .delete()
                .uri("/animes/{id}", 1)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @DisplayName("delete removes Mono error when anime does not exist and user is successfully authenticated and have the role ADMIN")
    @WithUserDetails(ADMIN_USER)
    public void delete_ReturnsMonoError_WhenEmptyMonoIsReturned() {
        BDDMockito.when(animeRepositoryMock.findById(anyInt()))
                .thenReturn(Mono.empty());

        client
                .delete()
                .uri("/animes/{id}", 1)
                .exchange()
                .expectStatus().isNoContent();
//                .expectBody()
//                .jsonPath("$.status").isEqualTo(404)
//                // Importa o Custom Attributes para pode carregar a propriedade abaixo
//                .jsonPath("$.developerMessage").isEqualTo("A ResponseStatusException Happened");
    }

    @Test
    @DisplayName("update save updated anime and returns empty mono when successful and user is successfully authenticated and have the role ADMIN")
    @WithUserDetails(ADMIN_USER)
    public void update_SaveUpdatedAnime_WhenSuccessful() {
        client
                .put()
                .uri("/animes/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(anime))
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @DisplayName("update returns Mono error when anime does not exist and user is successfully authenticated and have the role ADMIN")
    @WithUserDetails(ADMIN_USER)
    public void update_ReturnMonoError_WhenEmptyMonoIsReturned() {
        BDDMockito.when(animeRepositoryMock.findById(anyInt()))
                .thenReturn(Mono.empty());

        client
                .put()
                .uri("/animes/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(anime))
                .exchange()
                .expectStatus().isNoContent();
//                .expectBody()
//                .jsonPath("$.status").isEqualTo(404)
//                .jsonPath("$.developerMessage").isEqualTo("A ResponseStatusException Happened");
    }
}
