package com.alkl1m.contractor.config;

import com.alkl1m.auditlogspringbootautoconfigure.annotation.EnableHttpLogging;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableHttpLogging
public class ApplicationConfig implements WebMvcConfigurer {

}
