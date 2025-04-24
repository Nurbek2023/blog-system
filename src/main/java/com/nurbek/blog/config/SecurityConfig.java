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
                // 1. Allow access to Swagger UI, H2 console, and test endpoints
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/h2-console/**",
                                "/api/test/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .anyRequest().permitAll() // allow all other requests for now (dev only!)
                )

                // 2. Disable CSRF protection for dev tools (Swagger, H2)
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                "/h2-console/**",
                                "/api/test/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        )
                        .disable()
                )

                // 3. Allow H2 console to be opened in iframe
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                )

                // 4. Optional: No sessions, no explicit security context saving
                .securityContext(securityContext -> securityContext.requireExplicitSave(false))
                .sessionManagement(session -> session.disable());

        return http.build();
    }
}
