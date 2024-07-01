package com.alkl1m.contractor.config;

import com.alkl1m.auditlogspringbootautoconfigure.annotation.EnableHttpLogging;
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

}
