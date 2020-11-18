package io.github.wendergalan.springrestrepositories.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.MediaType;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The type Rest mvc configuration.
 */
@Configuration
public class RestMvcConfiguration implements RepositoryRestConfigurer {

    private static final List<String> EVENTS;

    @Autowired
    private LocalValidatorFactoryBean beanValidator;

    /**
     * Eventos disponiveis para adicionar ao Bean Validation
     */
    static {
        List<String> events = new ArrayList<String>();
        events.add("beforeCreate");
        events.add("afterCreate");
        events.add("beforeSave");
        events.add("afterSave");
        events.add("beforeLinkSave");
        events.add("afterLinkSave");
        events.add("beforeDelete");
        events.add("afterDelete");
        EVENTS = Collections.unmodifiableList(events);
    }

    /**
     * ALTERAR PROPRIEDADES DO REST REPOSITORIES
     * https://docs.spring.io/spring-data/rest/docs/current/reference/html/#getting-started.changing-other-properties
     */
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        // configs of rest repositories
        config.setBasePath("/api")
                // default page size
                .setDefaultPageSize(30)
                // mas page size
                .setMaxPageSize(100)
                // default media type
                .setDefaultMediaType(MediaType.APPLICATION_JSON)
                // retorna o item salvo no post e put
                .setReturnBodyOnCreate(true)
                // https://docs.spring.io/spring-data/rest/docs/current/reference/html/#getting-started.setting-repository-detection-strategy
                // Forms of configuration: https://www.programcreek.com/java-api-examples/index.php?api=org.springframework.data.rest.core.config.RepositoryRestConfiguration
                .setRepositoryDetectionStrategy(RepositoryDetectionStrategy.RepositoryDetectionStrategies.ANNOTATED);

        // Pode especificar as projeções diretamente aq ou nos subpacotes de onde as entidades estão.
        // config.getProjectionConfiguration().addProjection();
    }

    /**
     * Configura os serializadores e deserializadores da aplicação.
     *
     * @param objectMapper
     */
    @Override
    public void configureJacksonObjectMapper(ObjectMapper objectMapper) {
        // Registrar os serializadores deserializadores
    }

    /**
     * Configure os validadores da entidade. Colocado por padrão o do próprio validation.
     *
     * @param validatingListener
     */
    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
        validatingListener.addValidator("beforeCreate", beanValidator);
    }
}
