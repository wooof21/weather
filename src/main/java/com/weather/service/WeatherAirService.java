package com.weather.service;

import com.weather.helper.UnifiedMapper;
import com.weather.model.UnifiedCityData;
import com.weather.model.UnifiedResponse;
import com.weather.model.WeatherHistoryData;
import com.weather.model.airquality.AirQualityRes;
import com.weather.model.airquality.error.AQIRes;
import com.weather.model.weather.WeatherRes;
import com.weather.repos.WeatherRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class WeatherAirService {

    @Autowired
    @Qualifier("weatherClient")
    private WebClient weatherClient;

    @Autowired
    @Qualifier("airQualityClient")
    private WebClient airClient;

    @Autowired
    protected UnifiedMapper mapper;

    @Autowired
    protected WeatherRepo repo;

    public Mono<WeatherHistoryData> getWeatherHistoryDataByCityName(String cityName) {
        return repo.findAllWeathersByCity(cityName).collectList()
                .flatMap(histories -> histories.isEmpty() ? Mono.empty() : Mono.just(
                        WeatherHistoryData.builder()
                                .status("success")
                                .weatherHistory(histories)
                                .errors(Collections.emptyList())
                                .build()
                ))
                .switchIfEmpty(Mono.defer(() -> {
                    WeatherHistoryData empty = WeatherHistoryData.builder()
                            .status("Not Found")
                            .weatherHistory(Collections.emptyList())
                            .errors(List.of("Weather History Data Not Found For City: " + cityName))
                            .build();
                    return Mono.just(empty);
                }));
    }

    public Mono<UnifiedResponse> getUnifiedWeatherAirData(String cityName) {
        Mono<WeatherRes> weather = getCurrentWeather(cityName);
        Mono<AirQualityRes> airQuality = getAirQuality(cityName);

        return Mono.zip(weather, airQuality)
                .map(pair -> mapper.map(pair.getT1(), pair.getT2()))
                .flatMap(weatherRes -> {
                    UnifiedCityData weatherData = weatherRes.getData();
                    return repo.save(weatherData)
                            .doOnNext(savedEntity -> log.info("Saved: {}", savedEntity))
                            .thenReturn(weatherRes);
                });

    }

    public Mono<UnifiedResponse> getUnifiedWeatherAirError(String cityName) {
        Mono<WeatherRes> weather = getCurrentWeather(cityName);
        Mono<AQIRes> airQuality = getCurrentAirQuality(cityName);

        return Mono.zip(weather, airQuality, (we, aqi) -> mapper.mapError(we, aqi));
    }

    protected Mono<WeatherRes> getCurrentWeather(String cityName) {
        log.info("getCurrentWeather start");
        return weatherClient.get()
                .uri(uri -> uri.queryParam("city", cityName).build())
                .retrieve()
                .bodyToMono(WeatherRes.class)
                .doOnSuccess(weatherRes -> log.info("weather: {}", weatherRes))
                .onErrorResume(e -> {
                    log.error("getCurrentWeather Error: {}", e.getMessage());
                    return Mono.just(WeatherRes.builder().error(e.getMessage()).build());
                });
    }
    protected Mono<AQIRes> getCurrentAirQuality(String cityName) {
        log.info("getCurrentAirQuality start");
        return weatherClient.get()
                .uri(uri -> uri.path("/airquality").queryParam("city", cityName).build())
                .retrieve()
                .bodyToMono(AQIRes.class)
                .doOnSuccess(aqiRes -> log.info("aqi: {}", aqiRes))
                .onErrorResume(e -> {
                    log.error("getCurrentAirQuality Error: {}", e.getMessage());
                    return Mono.just(AQIRes.builder().error(e.getMessage()).build());
                });
    }

    protected Mono<AirQualityRes> getAirQuality(String cityName) {
        log.info("getAirQuality start");
        return airClient.get()
                .uri(uri -> uri.path("/{cityName}").build(cityName))
                .retrieve()
                .bodyToMono(AirQualityRes.class)
                .doOnSuccess(airQualityRes -> log.info("airQuality: {}", airQualityRes))
                .onErrorResume(e -> {
                    log.error("getAirQuality Error: {}", e.getMessage());
                    return Mono.just(AirQualityRes.builder().error(e.getMessage()).build());
                });
    }
}
