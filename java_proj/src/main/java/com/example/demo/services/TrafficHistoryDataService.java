package com.example.demo.services;

import com.example.demo.models.TrafficHistoryModel;
import com.example.demo.data.retriever.TrafficHistoryDataFetcher;
import javafx.scene.chart.XYChart;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class TrafficHistoryDataService {
    private final TrafficHistoryDataFetcher trafficHistoryDataFetcher;

    public TrafficHistoryDataService() {
        this.trafficHistoryDataFetcher = new TrafficHistoryDataFetcher();
    }

    public XYChart.Series<String, Number> fetchData(String tmsPointId, int yearShort, int dayNumber) {
        // Create a series for the chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        series.setName("Vehicle Speed Year 20%d ,day %d from start of year, tms point id %s".formatted(yearShort, dayNumber, tmsPointId));

        List<TrafficHistoryModel> trafficHistoryModelList = null;
        try {
            trafficHistoryModelList = this.trafficHistoryDataFetcher.fetchData(tmsPointId, yearShort, dayNumber);
        } catch (Exception e) {
            series.setName("Try another location or date\nNo data available from digitraffic data year 20%d ,day %d from start of year, tms point id %s".formatted(yearShort, dayNumber, tmsPointId));
        }

        if (trafficHistoryModelList != null && trafficHistoryModelList.size() > 0) {
            // Iterate through the hours of the day
            for (int hour = 0; hour < 24; hour++) {
                // Filter data for the current hour
                int finalHour = hour;
                List<TrafficHistoryModel> hourData = trafficHistoryModelList.stream()
                        .filter(data -> data.getHour() == finalHour)
                        .toList();

                // Calculate the average speed for the hour
                double averageSpeed = hourData.stream()
                        .mapToDouble(TrafficHistoryModel::getSpeed)
                        .average()
                        .orElse(0.0); // Default to 0 if there's no data for the hour

                // Create a LocalDateTime for the current hour
                LocalDateTime dateTime = LocalDateTime.of(LocalDate.ofYearDay(2000 + yearShort, dayNumber), LocalTime.of(hour, 0));

                // Add the data point to the series
                series.getData().add(new XYChart.Data<>(dateTime.format(DateTimeFormatter.ofPattern("HH")), averageSpeed));
            }
        }

        return series;
    }
}
