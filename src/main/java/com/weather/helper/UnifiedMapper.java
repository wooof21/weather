package com.weather.helper;

import com.weather.model.UnifiedCityData;
import com.weather.model.UnifiedResponse;
import com.weather.model.airquality.AirQualityRes;
import com.weather.model.airquality.error.AQIRes;
import com.weather.model.weather.WeatherDataCurrent;
import com.weather.model.weather.WeatherRes;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class UnifiedMapper {

    public UnifiedResponse map(WeatherRes weather, AirQualityRes airQuality) {
        return UnifiedResponse.builder()
                .status(setStatus(weather, airQuality))
                .data(setData(weather, airQuality))
                .errors(setErrors(weather, airQuality))
                .build();
    }

    private String setStatus(WeatherRes weather, AirQualityRes airQuality) {
        if(ObjectUtils.isEmpty(weather.getError()) && ObjectUtils.isEmpty(airQuality.getError())) return "success";

        if(!ObjectUtils.isEmpty(weather.getError()) && !ObjectUtils.isEmpty(airQuality.getError())) return "failed";

        return "partial";
    }

    public UnifiedCityData setData(WeatherRes weather, AirQualityRes airQuality) {
        UnifiedCityData cityData = UnifiedCityData.builder().build();
        if(ObjectUtils.isEmpty(weather.getError()) && ObjectUtils.nullSafeEquals(weather.getCount(), 1)) {
            WeatherDataCurrent weatherData = weather.getData().get(0);
            cityData.setCity(weatherData.getCityName());
            cityData.setCountry(weatherData.getCountryCode());
            cityData.setTemperature(weatherData.getTemp());
            cityData.setWeatherDescription(weatherData.getWeather().getDescription());
//            cityData.setTimestamp(setTime(weather));
            cityData.setWeatherTimestamp(weather.getData().get(0).getTimestamp());
            cityData.setTimezone(weather.getData().get(0).getTimezone());
        }

        if(ObjectUtils.isEmpty(airQuality.getError())) {
            cityData.setAqi(airQuality.getData().getAqi());
            cityData.setAirQualityStatus(AqiRange.fromAqi(airQuality.getData().getAqi()));
        }
        return cityData;
    }

    private List<String> setErrors(WeatherRes weather, AirQualityRes airQuality) {
        List<String> errors = new ArrayList<>();
        if(!ObjectUtils.isEmpty(weather.getError())) {
            errors.add(weather.getError());
        }
        if(!ObjectUtils.isEmpty(airQuality.getError())) {
            errors.add(airQuality.getError());
        }
        if(!ObjectUtils.isEmpty(weather.getCount()) && weather.getCount() > 1) {
            errors.add("City Weather Data Is Not Unique.");
        }
        return errors;
    }


    public UnifiedResponse mapError(WeatherRes weather, AQIRes aqi) {
        return UnifiedResponse.builder()
                .status(setErrorStatus(weather, aqi))
                .data(setDataError(weather, aqi))
                .errors(setAqiErrors(weather, aqi))
                .build();
    }

    private String setErrorStatus(WeatherRes weather, AQIRes aqi) {
        if(ObjectUtils.isEmpty(weather.getError()) && ObjectUtils.isEmpty(aqi.getError())) return "success";

        if(!ObjectUtils.isEmpty(weather.getError()) && !ObjectUtils.isEmpty(aqi.getError())) return "failed";

        return "partial";
    }

    private ZonedDateTime setTime(WeatherRes weather) {
        String zone = weather.getData().get(0).getTimezone();
        Long ts = weather.getData().get(0).getTimestamp();
        ZoneId zoneId = ZoneId.of(zone);
        Instant instant = Instant.ofEpochSecond(ts);
        return instant.atZone(zoneId);
    }

    private UnifiedCityData setDataError(WeatherRes weather, AQIRes aqi) {
        UnifiedCityData cityData = UnifiedCityData.builder().build();
        if(ObjectUtils.isEmpty(weather.getError()) && ObjectUtils.nullSafeEquals(weather.getCount(), 1)) {
            WeatherDataCurrent weatherData = weather.getData().get(0);
            cityData.setCity(weatherData.getCityName());
            cityData.setCountry(weatherData.getCountryCode());
            cityData.setTemperature(weatherData.getTemp());
            cityData.setWeatherDescription(weatherData.getWeather().getDescription());
//            cityData.setTimestamp(setTime(weather));
            cityData.setWeatherTimestamp(weather.getData().get(0).getTimestamp());
            cityData.setTimezone(weather.getData().get(0).getTimezone());
        }

        if(ObjectUtils.isEmpty(aqi.getError())) {
            cityData.setAqi(aqi.getData().get(0).getAqi());
            cityData.setAirQualityStatus(AqiRange.fromAqi(aqi.getData().get(0).getAqi()));
        }
        return cityData;
    }

    private List<String> setAqiErrors(WeatherRes weather, AQIRes aqi) {
        List<String> errors = new ArrayList<>();
        if(!ObjectUtils.isEmpty(weather.getError())) {
            errors.add(weather.getError());
        }
        if(!ObjectUtils.isEmpty(aqi.getError())) {
            errors.add(aqi.getError());
        }
        if(!ObjectUtils.isEmpty(weather.getCount()) && weather.getCount() > 1) {
            errors.add("More Than 1 City Weather Data Returned.");
        }
        return errors;
    }
}
