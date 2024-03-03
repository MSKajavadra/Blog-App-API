package com.project.blogapp.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(getInfo())
                .externalDocs(new ExternalDocumentation()
                        .description("Extra Documents")
                        .url("Link of External Docs"));
    }
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("**").displayName("default")
                .pathsToMatch("/blog/**")
                .build();
    }
    private Info getInfo(){
        return new Info().title("Blogging Application Backend")
                .description("This project is develop by Maulik Kajavadra")
                .version("v0.0.1")
                .termsOfService("Terms of Service")
                .contact(new Contact().name("Maulik").email("mskajavadra@gmail.com").url("https://mskajavadra.com"))
                .license(new License().name("License of APIs").url("API License URL"))
                .extensions(Collections.emptyMap());
    }
}
