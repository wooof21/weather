package com.weather.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Configuration
public class WebClientConfig {

    @Autowired
    private ApiConfig apiConfig;

    @Bean(name = "weatherClient")
    public WebClient weatherClient() {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(weatherUriBuilder());
        return WebClient.builder()
                .uriBuilderFactory(factory)
                .build();
    }

    private UriComponentsBuilder weatherUriBuilder() {
        return UriComponentsBuilder.fromUriString(apiConfig.getWeatherApiBase())
                .queryParam("key", apiConfig.getWeatherApiKey());
    }

    @Bean(name = "airQualityClient")
    public WebClient airQualityClient() {
        return WebClient.builder()
                .baseUrl(apiConfig.getAirQualityBase())
                .filter((req, next) -> {
                    URI newUri = UriComponentsBuilder.fromUri(req.url())
                            .queryParam("token", apiConfig.getAirQualityKey())
                            .build(true)
                            .toUri();
                    ClientRequest newReq = ClientRequest.from(req)
                            .url(newUri)
                            .build();
                    return next.exchange(newReq);
                })
                .build();
    }
}
