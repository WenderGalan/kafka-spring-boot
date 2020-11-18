package wendergalan.github.io.webflux.util;

import wendergalan.github.io.webflux.domain.Anime;

public class AnimeCreator {

    public static Anime createAnimeToBeSaved() {
        return Anime.builder()
                .name("Tensei Shitara Slime Datta Ken")
                .build();
    }

    public static Anime createValidAnime() {
        return Anime.builder()
                .id(1)
                .name("Tensei Shitara Slime Datta Ken")
                .build();
    }

    public static Anime createValidUpdateAnime() {
        return Anime.builder()
                .id(1)
                .name("Tensei Shitara Slime Datta Ken 2")
                .build();
    }
}
