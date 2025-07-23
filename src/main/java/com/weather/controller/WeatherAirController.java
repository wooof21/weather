package com.weather.controller;

import com.weather.model.UnifiedResponse;
import com.weather.model.WeatherHistoryData;
import com.weather.service.WeatherAirService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class WeatherAirController {

    @Autowired
    private WeatherAirService weatherAirService;

    @GetMapping("/history")
    public Mono<WeatherHistoryData> getWeatherDataByCityName(@RequestParam(name = "city") String cityName) {
        return weatherAirService.getWeatherHistoryDataByCityName(cityName);
    }

    @GetMapping("/data")
    public Mono<UnifiedResponse> getWeatherAirByCity(@RequestParam(name = "city") String cityName) {
        return weatherAirService.getUnifiedWeatherAirData(cityName);
    }

    @GetMapping("/aqi")
    public Mono<UnifiedResponse> getWeatherAirError(@RequestParam(name = "city") String cityName) {
        return weatherAirService.getUnifiedWeatherAirError(cityName);
    }
}
