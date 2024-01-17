package com.example.demo.models;

import com.example.demo.controllers.JsonReaderController;
import javafx.beans.property.*;

public class CameraModel {
    private BooleanProperty isTrafficCameraViewVisible;
    private JsonReaderController jsonReader = new JsonReaderController();

    public CameraModel() {
        UserPreferenceModel data = jsonReader.readJsonData();
        if (data != null) {
            isTrafficCameraViewVisible = new SimpleBooleanProperty(data.isShowTrafficCamera());
        } else {
            isTrafficCameraViewVisible = new SimpleBooleanProperty(true);
        }
    }

    public void toggleImageVisibility() {
        isTrafficCameraViewVisible.setValue(!isTrafficCameraViewVisible.getValue());
    }

    public BooleanProperty getTrafficCameraViewVisibilityProperty() {
        return isTrafficCameraViewVisible;
    }
}
