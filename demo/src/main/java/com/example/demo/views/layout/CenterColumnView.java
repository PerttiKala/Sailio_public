package com.example.demo.views.layout;

import com.example.demo.views.TrafficTemperatureChartView;
import javafx.scene.layout.VBox;

public class CenterColumnView implements LayoutViews{
    VBox view = new VBox(10);

    public VBox getView() {
        return view;
    }

    public CenterColumnView() {
        // Create a VBox for the center column
        TrafficTemperatureChartView trafficTemperatureChartView = new TrafficTemperatureChartView("Fetch traffic data", "Traffic chart");
        view.getChildren().addAll(
                trafficTemperatureChartView.getView()
        );
    }
}
