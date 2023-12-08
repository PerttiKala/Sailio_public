package com.example.demo.services;

import com.example.demo.models.TrafficTemperatureChartModel;
import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;

public class CustomizeUIService {
    private TrafficTemperatureChartModel model = new TrafficTemperatureChartModel();

    public BooleanProperty getTrafficSeriesVisibilityProperty(){
        return model.getTrafficSeriesVisibilityProperty();
    }
    public BooleanProperty getTemperatureSeriesVisibilityProperty(){
        return model.getTemperatureSeriesVisibilityProperty();
    }

    public void bindTemperatureSeriesVisibility(XYChart.Series<String, Number> series) {

        Node node = series.getNode(); // Get the node associated with the series
        if (node != null) {
            node.visibleProperty().bind(model.getTemperatureSeriesVisibilityProperty());
        } else {
            // Handle the case when the node is null, perhaps by logging an error or taking an appropriate action.
        }

        for (XYChart.Data<String, Number> data : series.getData()) {
            Node dataNode = data.getNode();
            if (dataNode != null) {
                dataNode.visibleProperty().bind(model.getTemperatureSeriesVisibilityProperty());
            } else {
                // Handle the case when the data node is null, perhaps by logging an error or taking an appropriate action.
            }
        }
    }

    public void toggleTemperatureSeriesVisibility() {
        model.toggleTemperatureSeriesVisibility();
    }

    public void bindTrafficSeriesVisibility(XYChart.Series<String, Number> series) {
        Node node = series.getNode(); // Get the node associated with the series
        if (node != null) {
            node.visibleProperty().bind(model.getTrafficSeriesVisibilityProperty());
        } else {
            // Handle the case when the node is null, perhaps by logging an error or taking an appropriate action.
        }

        for (XYChart.Data<String, Number> data : series.getData()) {
            Node dataNode = data.getNode();
            if (dataNode != null) {
                dataNode.visibleProperty().bind(model.getTrafficSeriesVisibilityProperty());
            } else {
                // Handle the case when the data node is null, perhaps by logging an error or taking an appropriate action.
            }
        }
    }

    public void toggleTrafficSeriesVisibility() {
        model.toggleTrafficSeriesVisibility();
    }
}
