package com.example.demo.controllers;

import com.example.demo.services.CustomizeUIService;
import com.example.demo.services.WeatherService;
import javafx.beans.property.BooleanProperty;
import javafx.scene.chart.XYChart;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;

public class TrafficTemperatureChartController {
    private static final TrafficTemperatureChartController instance = new TrafficTemperatureChartController();
    private WeatherService weatherService;
    private CustomizeUIService uiService;

    private TrafficTemperatureChartController() {
        this.weatherService = new WeatherService();
        this.uiService = new CustomizeUIService();
    }

    public static TrafficTemperatureChartController getInstance() {
        if (instance != null) {
            return instance;
        }
        return new TrafficTemperatureChartController();
    }


    public XYChart.Series<String, Number> getWeatherData(String latitude, String longitude, LocalDate startDate, LocalDate endDate) throws IOException, ParseException {
        return weatherService.getWeatherData(latitude, longitude, startDate, endDate);
    }

    public void bindTemperatureSeriesVisibility(XYChart.Series<String, Number> series) {
        uiService.bindTemperatureSeriesVisibility(series);
    }
    public BooleanProperty getTrafficSeriesVisibilityProperty(){
        return uiService.getTrafficSeriesVisibilityProperty();
    }
    public BooleanProperty getTemperatureSeriesVisibilityProperty(){
        return uiService.getTemperatureSeriesVisibilityProperty();
    }

    public void toggleTemperatureSeriesVisibility() {
        uiService.toggleTemperatureSeriesVisibility();
    }

    public void bindTrafficSeriesVisibility(XYChart.Series<String, Number> series) {
        uiService.bindTrafficSeriesVisibility(series);
    }

    public void toggleTrafficSeriesVisibility() {
        uiService.toggleTrafficSeriesVisibility();
    }
}
