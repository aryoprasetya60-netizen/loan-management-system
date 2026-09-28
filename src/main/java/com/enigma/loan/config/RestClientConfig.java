package com.enigma.loan.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${api.jsonplaceholder.base-url:https://jsonplaceholder.typicode.com/}")
    private String jsonPlaceholderBaseUrl;

    // intercept(satpam) untuk logging request dan response
    private ClientHttpRequestInterceptor loggingInterceptor() {
        return ((request, body, execution) -> {
            System.out.println("Request : " + request.getMethod() + " " + request.getURI());
            System.out.println("Headers : " + request.getHeaders());
            System.out.println("Body : " + new String(body));
            return execution.execute(request,body);
        });
    }

    // konfigurasi timeout
    private SimpleClientHttpRequestFactory timeoutConfiguration() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000); // 5 detik otomatis timeout
        factory.setReadTimeout(5000); // 5 detik otomatis timeout
        return factory;
    }

    // Bean Primary
    @Bean
    @Primary
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl(jsonPlaceholderBaseUrl)
                .build();
    }


    // Bean kedua
    @Bean("placeholderClient")
    public RestClient placeholderClient() {
        return RestClient.builder()
                .baseUrl(jsonPlaceholderBaseUrl)
                // default header yang akan disertakan disetiap request
                .defaultHeader(HttpHeaders.ACCEPT, "application/json")
                .defaultHeader("X-Custom-Header", "RestClient Demo")
                // request interceptor
                .requestInterceptor(loggingInterceptor())
                .requestFactory(timeoutConfiguration())
                .build();
    }
}
