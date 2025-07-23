package com.weather.model.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeatherDataCurrent {

    @JsonProperty("app_temp")
    private Double temp;

    @JsonProperty("city_name")
    private String cityName;

    @JsonProperty("country_code")
    private String countryCode;
    @JsonProperty("weather")
    private Weather weather;

    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("ts")
    private Long timestamp;

}
