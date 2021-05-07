package ru.rplaton.labforward.coding.challenge.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Labforward Challenge API")
                        .description("Text analytics demo application for coding challenge.")
                        .version("v0.0.1")
                        .contact(new Contact().name("Roman Platonov").email("rom16rus@gmail.com")));
    }
}
