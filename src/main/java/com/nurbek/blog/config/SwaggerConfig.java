package com.nurbek.blog.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI blogApiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Nurbek's Blog API")
                        .description("Spring Boot REST API for a blogging system")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Nurbek")
                                .email("nurbek@example.com")));
    }
}