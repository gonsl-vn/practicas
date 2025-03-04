package com.viewnext.practica1.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
                new Info().title("Practica 1.2").description("Ejercicio Practica 1.2 viewnext").version("2.0").contact(
                                new Contact().name("Javier Arias Rodriguez").url("url").email("javier.arias@viewnext.com"))
                        .license(new License().name("LICENSE").url("LICENSE URL")));
    }
}
