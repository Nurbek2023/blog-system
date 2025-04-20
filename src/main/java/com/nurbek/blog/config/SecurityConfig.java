package com.nurbek.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**", "/api/test/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().permitAll()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**", "/api/test/**", "/swagger-ui/**", "/v3/api-docs/**")
                        .disable()
                )
                .headers(headers -> headers
                        .defaultsDisabled()
                        .cacheControl(c -> c.disable()) // disable cache if you want, or keep it
                        .frameOptions(frame -> frame.sameOrigin())
                )
                .securityContext(securityContext -> securityContext.requireExplicitSave(false)) // optional: don't require explicit saving of security context
                .sessionManagement(session -> session.disable()); // disable sessions

        return http.build();
    }
}
