package com.example.demo.models;

import com.example.demo.data.mappers.WeatherData;

import java.util.ArrayList;

public class WeatherDataModel {
    public String TemperatureUnit;
    public final ArrayList<WeatherData> weatherData;

    public WeatherDataModel(String temperatureUnit, ArrayList<WeatherData> weatherData) {
        this.TemperatureUnit = temperatureUnit;
        this.weatherData = weatherData;
    }

}
