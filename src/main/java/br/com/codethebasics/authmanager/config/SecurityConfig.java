package br.com.codethebasics.authmanager.config;

import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Classe de configuração de segurança da aplicação.
 *
 * @author codethebasics
 */
@Log
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        log.info("-------------------------------");
        log.info("-- Configuring HTTP Security...");
        log.info("-------------------------------");

        http
            .authorizeRequests(authorize -> {
                authorize
                    .antMatchers("/h2-console/**").permitAll()
                    .antMatchers("/api/v1/public/**").permitAll()
                    .antMatchers("/api/v1/protected/**").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/v1/private/**").hasRole("ADMIN");
            })
            .authorizeRequests()
            .anyRequest().authenticated()
            .and().httpBasic()
            .and().headers().frameOptions().disable()
            .and().csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}