package com.example.demo.services;
import com.example.demo.TrafficWeatherApp;
import com.example.demo.models.UserPreferenceModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Objects;


public class JsonReaderService {
    public UserPreferenceModel readJsonData() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            UserPreferenceModel data = objectMapper.readValue(new File("user-preference.json"), UserPreferenceModel.class);
            return data;
        } catch (Exception e) {
             e.printStackTrace();
            return null;
        }
    }
}
