package io.github.wendergalan.springrestrepositories.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.wendergalan.springrestrepositories.util.DataUtil;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.validation.MessageInterpolator;
import java.time.LocalDate;
import java.util.Locale;

/**
 * The type Application configuration.
 */
@Configuration
@ComponentScan
public class ApplicationConfiguration implements WebMvcConfigurer {

    /**
     * Object mapper object mapper.
     *
     * @return the object mapper
     */
    @Bean
    public ObjectMapper objectMapper() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        // Deserializa e Serealiza as datas para o padrão BR
        javaTimeModule.addSerializer(LocalDate.class, new DataUtil.LocalDateSerializer());
        javaTimeModule.addDeserializer(LocalDate.class, new DataUtil.LocalDateDeserializer());
        builder.modules(javaTimeModule);
        ObjectMapper mapper = builder.build();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        return mapper;
    }

    /**
     * Configs para internacionalização do projeto
     *
     * @return LocaleResolver locale resolver
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        // Padrão US
        // slr.setDefaultLocale(Locale.US);
        // Padrão BR
        slr.setDefaultLocale(new Locale("pt", "BR"));
        return slr;
    }

    /**
     * Message source reloadable resource bundle message source.
     *
     * @return the reloadable resource bundle message source
     */
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:/locale/messages", "org.hibernate.validator.ValidationMessages", "ValidationMessages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(3600); //refresh cache once per hour
        return messageSource;
    }

    /**
     * Locale change interceptor locale change interceptor.
     *
     * @return the locale change interceptor
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    /**
     * Default message interpolator message interpolator.
     *
     * @return the message interpolator
     */
    @Bean
    public MessageInterpolator defaultMessageInterpolator() {
        return new ResourceBundleMessageInterpolator(new MessageSourceResourceBundleLocator(messageSource()));
    }

    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource());
        return validator;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
