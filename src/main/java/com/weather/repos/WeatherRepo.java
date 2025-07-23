package com.weather.repos;

import com.weather.model.UnifiedCityData;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface WeatherRepo extends R2dbcRepository<UnifiedCityData, Integer> {

    Flux<UnifiedCityData> findAllWeathersByCity(String cityName);

}
