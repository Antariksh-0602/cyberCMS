package com.cyberCMS.cyberCMS.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final Path UPLOAD_DIR = Paths.get(System.getProperty("user.dir"), "uploads");

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String externalUploadPath = UPLOAD_DIR.toUri().toString();
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(externalUploadPath)
                .setCachePeriod(0);
    }
}
