package com.weather.model.airquality.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AQIRes {

    private String timezone;

    @JsonProperty("city_name")
    private String cityName;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("state_code")
    private String stateCode;

    private List<AQIData> data;

    private String error;
}
