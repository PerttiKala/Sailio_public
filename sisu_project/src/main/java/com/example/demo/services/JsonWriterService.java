package com.example.demo.services;

import com.example.demo.TrafficWeatherApp;
import com.example.demo.models.UserPreferenceModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class JsonWriterService {
    public void writeJsonData(UserPreferenceModel data) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File("user-preference.json"), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}