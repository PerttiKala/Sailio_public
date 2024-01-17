package com.example.demo.views;

import com.example.demo.controllers.TrafficCameraController;
import com.example.demo.controllers.TrafficTemperatureChartController;
import javafx.beans.property.BooleanProperty;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class CustomizeVisualizationView {
    private final VBox customizeVisualizationView = new VBox(10);
    private TrafficCameraController trafficCameraController = TrafficCameraController.getInstance();
    private TrafficTemperatureChartController trafficTempChartController = TrafficTemperatureChartController.getInstance();

    public VBox getView() {
        return customizeVisualizationView;
    }
    public BooleanProperty getTrafficSeriesVisibilityProperty(){
        return trafficTempChartController.getTrafficSeriesVisibilityProperty();
    }
    public BooleanProperty getTemperatureSeriesVisibilityProperty(){
        return trafficTempChartController.getTemperatureSeriesVisibilityProperty();
    }

    public BooleanProperty getTrafficCameraViewVisibilityProperty() {
        return trafficCameraController.getTrafficCameraViewVisibilityProperty();
    }

    public CustomizeVisualizationView() {
        Label title = new Label("Select visualization");


        CheckBox trafficDataCheckBox = new CheckBox("Show traffic data");
        trafficDataCheckBox.setOnAction(event -> trafficTempChartController.toggleTrafficSeriesVisibility());
        trafficDataCheckBox.setSelected(trafficTempChartController.getTrafficSeriesVisibilityProperty().getValue());

        CheckBox temperatureDataCheckBox = new CheckBox("Show temperature data");
        temperatureDataCheckBox.setOnAction(event -> trafficTempChartController.toggleTemperatureSeriesVisibility());
        temperatureDataCheckBox.setSelected(trafficTempChartController.getTemperatureSeriesVisibilityProperty().getValue());

        CheckBox cameraCheckBox = new CheckBox("Show traffic camera");
        cameraCheckBox.setOnAction(event -> trafficCameraController.toggleImageVisibility());
        cameraCheckBox.setSelected(trafficCameraController.getTrafficCameraViewVisibilityProperty().getValue());

        customizeVisualizationView.getChildren().addAll(
                title,
                trafficDataCheckBox,
                temperatureDataCheckBox,
                cameraCheckBox
        );
    }

}
