module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires okhttp3;
    requires org.json;
    requires com.fasterxml.jackson.databind;
    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.services;
    exports com.example.demo.controllers;
    opens com.example.demo.controllers to javafx.fxml;
    opens com.example.demo.services to javafx.fxml;
    exports com.example.demo.components;
    exports com.example.demo.serializers;
    exports com.example.demo.data.retriever to com.fasterxml.jackson.databind;
    exports com.example.demo.models to com.fasterxml.jackson.databind;
    opens com.example.demo.models to com.fasterxml.jackson.databind;
}