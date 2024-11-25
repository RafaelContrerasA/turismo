package com.example.Integradoraturismo.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import org.springframework.context.annotation.Bean;



public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Documentación de API - Integradora Turismo")
                        .description("API para el sistema de gestión de reservaciones")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Rafael Contreras A.")
                                .email("tu.email@dominio.com")
                                .url("https://tu-portafolio.com"))
                        .license(new License()
                                .name("Licencia MIT")
                                .url("https://opensource.org/licenses/MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("Repositorio del Proyecto")
                        .url("https://github.com/tu-repositorio"));
    }
    
    
}
