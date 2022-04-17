package edu.clevertec.check.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc  // аннотация разрешает нашему проекту использовать MVC
@ComponentScan("edu.clevertec.check.spring.controller") // указываем пакет с контроллерами
public class WebConfig implements WebMvcConfigurer {  // конфигурируем WebMvc

    // включаем авто-конфигурацию
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
