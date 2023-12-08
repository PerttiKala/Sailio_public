package com.example.demo.views.trafficCamera;

import com.example.demo.controllers.TrafficCameraController;
import javafx.scene.image.ImageView;

public class TrafficImageView extends ImageView {

        public TrafficImageView(TrafficCameraController trafficCameraController) {
            this.visibleProperty().bind(trafficCameraController.getTrafficCameraViewVisibilityProperty());
            this.setFitWidth(400);
            this.setPreserveRatio(true);
            this.setSmooth(true);
        }
}
