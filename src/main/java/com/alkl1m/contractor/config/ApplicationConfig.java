package com.alkl1m.contractor.config;

import com.alkl1m.auditlogspringbootautoconfigure.annotation.EnableHttpLogging;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Конфигурационный класс. Подключает логирование HTTP запросов и ответов.
 *
 * @author alkl1m
 */
@Configuration
@EnableHttpLogging
public class ApplicationConfig implements WebMvcConfigurer {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Contractor API")
                        .description("Contractor REST api")
                        .version("0.0.1-SNAPSHOT")
                );
    }

}
