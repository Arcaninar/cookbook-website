package dev.arcaninar.cookbook.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebCORSConfig implements WebMvcConfigurer {
    // TODO: temporary CORS Configuration, once frontend and backend has been deployed, modify the CORS Configuration to only allow from front-end
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/v1/**") // Allow all APIs under /api/v1
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
//                .allowCredentials(true);
    }
}