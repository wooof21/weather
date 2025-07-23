package com.weather.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class ApiConfig {


    @Value("${api.weather.key}")
    private String weatherApiKey;
    @Value("${api.weather.base}")
    private String weatherApiBase;
    @Value("${api.air_quality.key}")
    private String airQualityKey;
    @Value("${api.air_quality.base}")
    private String airQualityBase;
}
