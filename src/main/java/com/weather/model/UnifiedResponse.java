package com.weather.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UnifiedResponse {

    private String status;

    private UnifiedCityData data;

    private List<String> errors;
}
