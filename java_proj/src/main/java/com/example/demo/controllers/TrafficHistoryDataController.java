package com.example.demo.controllers;

import com.example.demo.services.TrafficHistoryDataService;
import javafx.scene.chart.XYChart;

public class TrafficHistoryDataController {

    private final TrafficHistoryDataService service = new TrafficHistoryDataService();

    public XYChart.Series<String, Number> fetchData(String tmsPointId, int yearShort, int dayNumber) {
        return this.service.fetchData(tmsPointId, yearShort, dayNumber);
    }
}
