package hr.fer.progi.rest;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConf implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry reg){
        reg.addMapping("/**").allowedOrigins("http://localhost:3000").allowedHeaders("*")
                .allowedMethods("GET", "POST", "DELETE", "OPTIONS").allowCredentials(true);
    }

}
