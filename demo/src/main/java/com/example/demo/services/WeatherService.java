package com.example.demo.services;

import com.example.demo.data.mappers.WeatherData;
import com.example.demo.data.retriever.WeatherDataFetcher;
import com.example.demo.models.WeatherDataModel;
import javafx.scene.chart.XYChart;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class WeatherService {

    WeatherDataFetcher weatherDataFetcher = new WeatherDataFetcher();

//    public Object fetchData(String latitude, String longitude, LocalDate start_date, LocalDate end_date) {
//        return null;
//    }

    public XYChart.Series<String, Number> getWeatherData(String latitude, String longitude, LocalDate startDate, LocalDate endDate) throws IOException, ParseException {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Temperature Data");
        
        WeatherDataModel dataArray = weatherDataFetcher.getData(latitude, longitude, startDate, endDate);

        for (WeatherData weatherData : dataArray.weatherData) {
            series.getData().add(new XYChart.Data<>(weatherData.getDateTime().format(DateTimeFormatter.ofPattern("HH")), weatherData.temperature));
        }

        return series;
    }
}
