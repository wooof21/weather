package com.weather.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;


@EnableWebFluxSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(ex -> ex
                                .anyExchange().permitAll()
//                        .pathMatchers("/public/**").permitAll() // optionally public
//                        .anyExchange().authenticated()
                )
//                .httpBasic(Customizer.withDefaults()) // Basic Auth enabled
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable) // disable basic auth
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable) // disable form login
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
