package com.example.demo.views;

import com.example.demo.controllers.LoginController;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

// Severi change
public class LoginView {

    public LoginController loginController = new LoginController();


    // Define UI elements
    private TextField usernameField;
    private PasswordField passwordField;
    private ToggleButton loginButton;

    public VBox getView() {
        return loginView;
    }

    private VBox loginView = new VBox(10);

    public LoginView() {
        // Create UI elements
        usernameField = new TextField();
        passwordField = new PasswordField();

        usernameField.setMaxWidth(150);
        passwordField.setMaxWidth(150);

        loginButton = new ToggleButton("Login");

        this.loginView.getChildren().addAll(
                new Label("Username:"),
                usernameField,
                new Label("Password:"),
                passwordField,
                loginButton
        );

        // Handle login button click
        loginButton.setOnAction(e -> toggleLogin());

    }

    private void toggleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (!loginButton.isSelected()) {
            // UserModel is logged in, change button text and action
            loginButton.setText("Login");
            // Logouts user
            loginController.logout();
            showNotification("Logout successful!");
            usernameField.setDisable(false);
            passwordField.setDisable(false);
        } else {
            if (!loginController.login(username, password)) {
                // Fields are empty, show an error message or disable the login button
                showError("Username and password are required. Min length 3 characters.");
                loginButton.setSelected(false); // Keep the button in the "Login" state
                return;
            }
            // UserModel is logged out, change button text and action
            loginButton.setText("Logout from user: " + username);
            // Login user with username and password
            loginController.login(username, password);
            showNotification("Logged in as : " + username);
            usernameField.setDisable(true);
            passwordField.setDisable(true);
        }
    }

    private void showError(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    private void showNotification(String notificationMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification");
        alert.setHeaderText(null);
        alert.setContentText(notificationMessage);
        alert.show();

        // Create a timeline to close the notification label after 5 seconds
        Duration duration = Duration.seconds(2);
        KeyFrame keyFrame = new KeyFrame(duration, e -> alert.close());
        Timeline timeline = new Timeline(keyFrame);
        timeline.setCycleCount(1); // Run once
        timeline.play();
    }
}
