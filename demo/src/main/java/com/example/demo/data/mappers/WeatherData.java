package com.example.demo.data.mappers;

import java.time.LocalDateTime;

public class WeatherData {
    public LocalDateTime date;
    public Double temperature;

    public WeatherData(LocalDateTime date, Double temperature) {
        this.date = date;
        this.temperature = temperature;
    }


    public Double getTemperature() {
        return temperature;
    }

    public LocalDateTime getDateTime() {
        return date;
    }
}
