package com.weather.service;

import com.weather.helper.UnifiedMapper;
import com.weather.model.UnifiedCityData;
import com.weather.model.airquality.AirQualityRes;
import com.weather.model.weather.WeatherRes;
import com.weather.repos.WeatherRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class WeatherGraphQlService extends WeatherAirService{


    public Mono<UnifiedCityData> saveCityWeatherData(String cityName) {
        Mono<WeatherRes> weather = getCurrentWeather(cityName);
        Mono<AirQualityRes> airQuality = getAirQuality(cityName);

        return Mono.zip(weather, airQuality)
                .map(pair -> mapper.setData(pair.getT1(), pair.getT2()))
                .log()
                .flatMap(repo::save);
    }

}
