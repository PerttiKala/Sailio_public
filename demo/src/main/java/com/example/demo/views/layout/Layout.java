package com.example.demo.views.layout;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.util.Objects;

public class Layout {
    // Create layout using BorderPane

    BorderPane borderPane = new BorderPane();
    Scene scene = new Scene(borderPane, 1200, 600);

    public Scene getView() {
        return scene;
    }

    public Layout() {
        borderPane.setPadding(new Insets(10));

        // Set the VBoxes in the BorderPane
        borderPane.setLeft(new LeftColumnView().getView());

        borderPane.setCenter(new CenterColumnView().getView());

        borderPane.setRight(new RightColumnView().getView());

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/styles.css")).toExternalForm());
    }
}
