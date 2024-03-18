package com.irfan.moneyger.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SwaggerConfiguration {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("Moneyger API").version("1.0.0")
                        .description("API for managing users money")
                        .contact(new Contact().name("Irfan Yahya Abdillah"))
                        .license(new License().name("License of API")
                                .url("API license URL")));
    }
}
