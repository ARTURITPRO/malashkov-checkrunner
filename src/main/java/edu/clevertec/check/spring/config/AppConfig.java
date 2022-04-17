package edu.clevertec.check.spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
// сканируем все компоненты, кроме тех, что аннотированы @EnableWebMvc
@ComponentScan(basePackages = {"edu.clevertec.check.spring", "edu.clevertec.check.service",
        "edu.clevertec.check.repository", "edu.clevertec.check.pdf"},
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class))
public class AppConfig {

    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }

}
