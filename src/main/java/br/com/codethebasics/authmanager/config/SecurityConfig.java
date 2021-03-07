package br.com.codethebasics.authmanager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * -----------------------------------------------------
 * Classe de configuração de segurança da aplicação.
 * - Gerencia regras de acesso aos recursos da aplicação
 * - Gerencia acesso à base de dados
 * -----------------------------------------------------
 * @author codethebasics
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${env}")
    private String env;

    @Value("${authmode}")
    private String authmode;

    private static final String IN_MEMORY = "inmemory";
    private static final String HTTP_BASIC = "httpbasic";
    private static final String JWT = "jwt";
    private static final String OAUTH = "oauth";

    /**
     * -------------------------------------------
     * Realiza a configurações gerais da aplicação
     * -------------------------------------------
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Para permitir a visualização da interface do H2
        http.headers().frameOptions().disable();

        configureAuthentication(http);

        configureAntMatchers(http);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * ---------------------------------------------
     * Realiza a triagem do processo de autenticação
     * ---------------------------------------------
     */
    private void configureAuthentication(HttpSecurity http) throws Exception {
        try {
            if (authmode == null) {
                authmode = IN_MEMORY;
            }
            switch (authmode) {
                case HTTP_BASIC: httpbasicAuthentication(http); break;
                case JWT: jwtAuthentication(http); break;
                case OAUTH: oauthAuthentication(http); break;
                default: inmemoryAuthentication(http); break;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ------------------------------------------
     * Configura acesso aos recursos da aplicação
     * ------------------------------------------
     */
    private void configureAntMatchers(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/v1/public/**").permitAll()
                .anyRequest().authenticated();
    }

    /**
     * ---------------------------------
     * Configura autenticação http basic
     * ---------------------------------
     */
    private void httpbasicAuthentication(HttpSecurity http) {
        System.out.println("httpbasicAuthentication");
    }

    /**
     * ------------------------------------
     * Configura autenticação via token JWT
     * ------------------------------------
     * @see {https://jwt.io/}
     */
    private void jwtAuthentication(HttpSecurity http) {
        System.out.println("jwtAuthentication");
    }

    /**
     * --------------------------------
     * Configura autenticação via oauth
     * --------------------------------
     * @see {https://oauth.net/2/}
     */
    private void oauthAuthentication(HttpSecurity http) {
        System.out.println("oauthAuthentication");
    }

    /**
     * -------------------------------------------
     * Configura autenticação em memória (default)
     * -------------------------------------------
     */
    private void inmemoryAuthentication(HttpSecurity http) {
        System.out.println("oauthAuthentication");
    }
}