package edu.clevertec.check.spring.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

// класс-ининциализатор веб-приложения
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    // передаем класс основной конфигурации
    // Этот метод должен содержать конфигурации которые инициализируют Beans
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{AppConfig.class};
    }
    
    // передаем класс веб конфигурации; тут можно добавить конфигурацию, в которой инициализируется ViewResolver
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class};
    }

    // определяем веб контекст приложения
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}