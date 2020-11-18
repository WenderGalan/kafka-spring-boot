package wendergalan.github.io.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;

@SpringBootApplication
public class SpringWebfluxEssentialsApplication {

    // Garante que o blockhound não vai travar nenhuma thread
    // Adicionar a VM options ao iniciar: -XX:+AllowRedefinitionToAddDeleteMethods
    static {
        BlockHound.install(builder -> builder.allowBlockingCallsInside("java.util.UUID", "randomUUID")
                .allowBlockingCallsInside("java.io.InputStream", "readNBytes")
                .allowBlockingCallsInside("java.io.FilterInputStream", "read")
        );
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringWebfluxEssentialsApplication.class, args);
    }

}
