package com.example.demo.facades;

import com.example.demo.controllers.TrafficHistoryDataController;
import com.example.demo.controllers.TrafficTemperatureChartController;
import com.example.demo.dto.LineChartFetchDto;
import com.example.demo.facades.interfaces.ILineChartFacade;
import javafx.scene.chart.XYChart;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class LineChartFacade implements ILineChartFacade<LineChartFetchDto> {
    private final TrafficHistoryDataController trafficHistoryDataController = new TrafficHistoryDataController();
    private final TrafficTemperatureChartController trafficTemperatureChartController = TrafficTemperatureChartController.getInstance();

    @Override
    public ArrayList<XYChart.Series<String, Number>> fetchData(LineChartFetchDto model) {
        ArrayList<XYChart.Series<String, Number>> result = new ArrayList<>();

        LocalDate selectedDate = model.getSelectedDate();

        int yearShort = selectedDate.getYear() % 100;
        int dayNumber = selectedDate.getDayOfYear();

        XYChart.Series<String, Number> trafficHistoryData = this.trafficHistoryDataController.fetchData(
                model.getLocation().getTmsPointId(),
                yearShort,
                dayNumber
        );
        result.add(trafficHistoryData);

        try {
            XYChart.Series<String, Number> weatherData = this.trafficTemperatureChartController.getWeatherData(
                    model.getLatitude(),
                    model.getLongitude(),
                    selectedDate,
                    selectedDate
            );

            result.add(weatherData);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        // Add code here if you need more data from another data sources

        return result;
    }
}
