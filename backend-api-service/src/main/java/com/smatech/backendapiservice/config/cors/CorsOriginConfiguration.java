package com.smatech.backendapiservice.config.cors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Configuration
@Slf4j
public class CorsOriginConfiguration {

    private static final List<String> ORIGINS = new ArrayList<>();

    static {
        ORIGINS.add("http://localhost:3001");
    }

    @Bean
    public CorsFilter corsFilter() {
        log.info("### Allowed origins : {}", ORIGINS);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(Boolean.TRUE);
        config.setAllowedOrigins(ORIGINS);
        config.setAllowedHeaders(Collections.singletonList(CorsConfiguration.ALL));
        config.setAllowedMethods(Collections.singletonList(CorsConfiguration.ALL));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source); // Return the actual CorsFilter
    }

}
