package com.enigma.loan.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI enigmaCademyOpenApi() {
        Server ngrokServer = new Server()
                .url("https://tidings-flashy-cure.ngrok-free.dev")
                .description("Server Produksi / Ngrok Tunnel");

        Server localServer = new Server()
                .url("http://localhost:8080")
                .description("Server Lokal (Development)");

        return new OpenAPI()
                .info(new Info()
                        .title("Loans App")
                        .description("Aplikasi Loans App")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Loans App")
                                .email("loansapp@email.com"))
                        .license(new License()
                                .name("Loans App")
                                .url("www.loans-app.com")))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("bearerAuth")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Masukkan JWT")))
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList("bearerAuth")
                )
                .servers(List.of(localServer, localServer));
    }
}
