package io.github.wendergalan.springrestrepositories.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * The type Swagger configuration.
 */
@Configuration
@EnableSwagger2WebMvc
@Import(SpringDataRestConfiguration.class)
public class SwaggerConfiguration {

    private static final String PATH_MAPPING = "/";

    private static final String PACKAGE_BASE = "io.github.wendergalan";

    @Value("${info.app.groupName}")
    private String groupName;

    @Value("${info.app.version}")
    private String projectVersion;

    @Value("${info.app.name}")
    private String projectName;

    @Value("${info.app.description}")
    private String projectDescription;

    /**
     * Rs api docket.
     *
     * @return the docket
     */
    @Bean
    Docket rsApi() {
        return new Docket(DocumentationType.SWAGGER_12)
                .groupName(groupName)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .pathMapping(PATH_MAPPING)
                .useDefaultResponseMessages(false);
    }

    /**
     * @return ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(projectName)
                .description(projectDescription)
                .version(projectVersion)
                .contact(new Contact("Wender Galan", "https://wendergalan.github.io/", "wendergalan2014@hotmail.com"))
                .license("Apache 2.0")
                .termsOfServiceUrl("https://github.com/WenderGalan/spring-rest-repositoy")
                .build();
    }
}
