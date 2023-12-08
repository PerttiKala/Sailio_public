package com.example.demo.views;

import com.example.demo.controllers.DateTimeController;
import com.example.demo.controllers.TrafficHistoryDataController;
import com.example.demo.controllers.TrafficTemperatureChartController;
import com.example.demo.dto.LineChartFetchDto;
import com.example.demo.facades.LineChartFacade;
import com.example.demo.models.Location;
import com.example.demo.services.DateTimeService;
import com.example.demo.services.LocationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class TrafficTemperatureChartView {

    private TrafficTemperatureChartController trafficTemperatureChartController = TrafficTemperatureChartController.getInstance();
    private TrafficHistoryDataController trafficHistoryDataController = new TrafficHistoryDataController();
    private LineChartFacade lineChartFacade = new LineChartFacade();

    private final DateTimeService dateTimeService = DateTimeService.getInstance();
    private final LocationService locationService = LocationService.getInstance();

    DateTimeController dateTimeController = DateTimeController.getInstance();

    private Button fetchDataButton;
    private CategoryAxis xAxis = new CategoryAxis();
    private NumberAxis yAxis = new NumberAxis();
    private final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);

    private final VBox trafficChartView = new VBox(10);

    public VBox getView() {
        return trafficChartView;
    }


    public TrafficTemperatureChartView(String buttonText, String lineChartTitle) {

        xAxis.setLabel("Time");
        yAxis.setLabel("Temperature (°C)");


//        xAxis.setAutoRanging(false);
        fetchDataButton = new Button(buttonText);
        fetchDataButton.getStyleClass().add("fetch-data-button");
        fetchDataButton.setOnAction(e -> {
            try {
                fetchLineChartData();
            } catch (IOException | ParseException ex) {
                throw new RuntimeException(ex);
            }
        });

        HBox hbox = new HBox();
        hbox.getStyleClass().add("spacing-10");
        hbox.getChildren().add(fetchDataButton);

        // Set line chart title
        lineChart.setTitle(lineChartTitle);
        lineChart.lookup(".chart-title").getStyleClass().add("text-title");

        this.trafficChartView.getChildren().addAll(
                lineChart,
                hbox
        );

    }

    private void fetchLineChartData() throws IOException, ParseException {
        // Set categories at every hour
        xAxis.setCategories(getHourlyCategories());

        lineChart.getData().clear(); // Clear previous data


        LineChartFetchDto lineChartFetchDto = LineChartFetchDto.createLineChartFetchDto(
                locationService.getSelectedLocation(),
                dateTimeController.getSelectedStartDate()
        );
        System.out.println(lineChartFetchDto.getLocation().getStationId());
        System.out.println(lineChartFetchDto.getLocation().getLocationName());
        ArrayList<XYChart.Series<String, Number>> series = lineChartFacade.fetchData(lineChartFetchDto);

        for (int i = 0; i < series.size(); i++) {
            XYChart.Series<String, Number> stringNumberSeries = series.get(i);
            lineChart.getData().add(stringNumberSeries);
            switch (i) {
                case 0 -> this.trafficTemperatureChartController.bindTrafficSeriesVisibility(stringNumberSeries);
                case 1 -> this.trafficTemperatureChartController.bindTemperatureSeriesVisibility(stringNumberSeries);
                // Add more cases if data added to series if you want to bind visibility
            }

        }

    }

    // Function to generate hourly categories (24-hour format)
    private static ObservableList<String> getHourlyCategories() {
        ObservableList<String> categories = FXCollections.observableArrayList();
        for (int hour = 0; hour < 24; hour++) {
            categories.add(String.format("%02d", hour));
        }
        return categories;
    }
}


