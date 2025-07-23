package com.weather.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.ZonedDateTime;


@Entity
@Table("weather_city_data")
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UnifiedCityData {

    @Id
    private Integer id;
    private String city;
    private String country;
    private Double temperature;
    @Column("weather_description")
    private String weatherDescription;
    private Integer aqi;
    @Column("air_quality_status")
    private String airQualityStatus;
    @Column("weather_timestamp")
    private Long weatherTimestamp;
    private String timezone;
}
