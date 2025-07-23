package com.weather.controller;

import com.weather.model.UnifiedCityData;
import com.weather.model.UnifiedResponse;
import com.weather.model.WeatherHistoryData;
import com.weather.service.WeatherAirService;
import com.weather.service.WeatherGraphQlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class WeatherGraphQL {
    @Autowired
    private WeatherAirService weatherAirService;

    @Autowired
    private WeatherGraphQlService weatherGraphQlService;

    @QueryMapping
    public Mono<WeatherHistoryData> weatherHistory(@Argument String city) {
        return weatherAirService.getWeatherHistoryDataByCityName(city);
    }

    @MutationMapping
    public Mono<UnifiedCityData> saveCityWeatherData(@Argument String city) {
        return weatherGraphQlService.saveCityWeatherData(city);
    }
}
