package com.example.demo;

import com.example.demo.views.layout.Layout;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
public class TrafficWeatherApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("TrafficWeatherApp");

        primaryStage.setScene(new Layout().getView());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}