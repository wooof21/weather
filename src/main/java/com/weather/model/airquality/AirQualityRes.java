package com.weather.model.airquality;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AirQualityRes {

    private String status;

    private AirQualityData data;

    private String error;
}
