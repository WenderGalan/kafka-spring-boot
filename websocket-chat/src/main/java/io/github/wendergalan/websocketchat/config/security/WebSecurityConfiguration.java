package io.github.wendergalan.websocketchat.config.security;

import io.github.wendergalan.websocketchat.component.MyBasicAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    public static String REALM = "WEBSOCKET";

    /**
     * Configure global security.
     *
     * @param auth the auth
     * @throws Exception the exception
     */
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/**").hasRole("ADMIN")
                .and()
                .httpBasic()
                .realmName(REALM)
                .authenticationEntryPoint(getBasicAuthEntryPoint())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(STATELESS);
    }

    /**
     * Gets basic auth entry point.
     *
     * @return the basic auth entry point
     */
    @Bean
    public MyBasicAuthenticationEntryPoint getBasicAuthEntryPoint() {
        return new MyBasicAuthenticationEntryPoint();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**");
    }
}
