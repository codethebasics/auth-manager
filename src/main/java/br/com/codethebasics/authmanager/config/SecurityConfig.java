package br.com.codethebasics.authmanager.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Classe de configuração de segurança da aplicação.
 *
 * @author codethebasics
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Value("${env}")
    private String env;

    @Value("${authmode}")
    private String authmode;

    private static final String INMEMORY = "inmemory";
    private static final String JWT = "jwt";
    private static final String OAUTH = "oauth";

    /**
     * Configura http security
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        log.info("-- Configuring HTTP Security...");

        http.cors().disable();
        http.headers().frameOptions().disable();

        http.authorizeRequests(authorize -> {
            authorize.antMatchers("/api/v1/public/**").permitAll();
        })
        .authorizeRequests()
        .anyRequest().authenticated()
        .and().httpBasic();

        log.info("\t[OK]");
    }

    /**
     * Configura a autenticação da aplicação
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        configureAuthentication(auth);
    }

    /**
     * Realiza a triagem do processo de autenticação
     */
    private void configureAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        try {
            log.info("-- Configuring authentication mode...");
            switch (authmode) {
                case JWT: jwtAuthentication(auth); break;
                case OAUTH: oauthAuthentication(auth); break;
                default: throw new Exception("\t[!] Authentication mode not provided. InMemoryAuthentication will be taken...");
            }
        }
        catch (Exception e) {
            log.warn(e.getMessage());
            inmemoryAuthentication(auth);
        }
    }

    /**
     * Configura autenticação via token JWT
     *
     * @see {https://jwt.io/}
     */
    private void jwtAuthentication(AuthenticationManagerBuilder auth) {
        log.info("\t[OK] {}", JWT);
    }

    /**
     * Configura autenticação via oauth
     *
     * @see {https://oauth.net/2/}
     */
    private void oauthAuthentication(AuthenticationManagerBuilder auth) {
        log.info("\t\t[OK] {}", OAUTH);
    }

    /**
     * Configura autenticação em memória
     */
    private void inmemoryAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        log.info("\t[OK] {}", INMEMORY);
        auth.inMemoryAuthentication()
                .withUser("testuser")
                .password(passwordEncoder().encode("testpass"))
                .roles("ADMIN");
    }

    /**
     * Ignora segurança para recursos específicos
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}