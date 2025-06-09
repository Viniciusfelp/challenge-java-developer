package br.com.neurotech.challenge.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Challenge Java API")
                        .version("1.0")
                        .description("This project evaluates and applies different credit modalities to individual clients based on specific criteria. " +
                                "The API allows for client registration and eligibility checks for various credit types."));
    }
}