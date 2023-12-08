package com.example.demo.models;

import com.example.demo.controllers.JsonReaderController;
import com.example.demo.controllers.JsonWriterController;
import javafx.beans.property.*;

public class TrafficTemperatureChartModel {
    private BooleanProperty isTrafficSeriesVisible;
    private BooleanProperty isTemperatureSeriesVisible;
        private JsonReaderController jsonReader = new JsonReaderController();


    public TrafficTemperatureChartModel() {
        UserPreferenceModel data = jsonReader.readJsonData();
        if (data != null) {
            isTrafficSeriesVisible = new SimpleBooleanProperty(data.isShowTrafficData());
            isTemperatureSeriesVisible = new SimpleBooleanProperty(data.isShowTempData());
        }else {
            isTrafficSeriesVisible = new SimpleBooleanProperty(true);
            isTemperatureSeriesVisible = new SimpleBooleanProperty(true);
        }
        System.out.println("trafftempchamodel");
    }

    public void toggleTrafficSeriesVisibility() {
        isTrafficSeriesVisible.setValue(!isTrafficSeriesVisible.getValue());
    }

    public BooleanProperty getTrafficSeriesVisibilityProperty() {
        return isTrafficSeriesVisible;
    }

    public void toggleTemperatureSeriesVisibility() {
        isTemperatureSeriesVisible.setValue(!isTemperatureSeriesVisible.getValue());
    }

    public BooleanProperty getTemperatureSeriesVisibilityProperty() {
        return isTemperatureSeriesVisible;
    }
}
