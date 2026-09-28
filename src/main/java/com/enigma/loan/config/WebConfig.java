package com.enigma.loan.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.cors.allowed-origins}")
    private String[] allowedOrigins;

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // izinkan origins tertentu
        corsConfiguration.setAllowedOrigins(Arrays.asList(allowedOrigins));

        // izinkan http method yang digunakan
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));

        // izinkan header tertentu
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization"));

        // izinkan kirim cookies dari domain berbeda
        corsConfiguration.setAllowCredentials(true);

        // izikan berapa lama browser menyimpan cache cors response
        corsConfiguration.setMaxAge(3600L);

        // izinkan expose authorization header ke client side
        corsConfiguration.setExposedHeaders(Collections.singletonList("Authorization"));

        // apply atau terapkan semua configurasi
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(source);
    }

}
