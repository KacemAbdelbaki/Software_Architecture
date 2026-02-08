package com.soft.archi.software_architecture.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class Config {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                authorizeRequests -> authorizeRequests
                        .requestMatchers("/**")
                        .permitAll()
                        .requestMatchers("/soft/archi/swagger-ui/**")
                        .permitAll()
                        .requestMatchers("/soft/archi/v3/api-docs*/**")
                        .permitAll());

        return http.build();
    }
}
