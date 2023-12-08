package com.example.demo.data.retriever;

import java.io.IOException;
import java.text.MessageFormat;

import javafx.scene.image.Image;
import com.example.demo.models.Location;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class TrafficCameraFetcher {
    private String endpoint = "https://tie.digitraffic.fi/api/weathercam/v1/stations/";

    public Image fetchCameraImage(Location location) {
        return fetchImageByURL(fetchCameraImageURL(location));
    }

    private String fetchCameraImageURL(Location location) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(endpoint + location.getWeatherStationId())
                .build();
        System.out.println("location.getWeatherStationId()  "+ location.getWeatherStationId());
        String imageUrl = null;
        try (Response response = client.newCall(request).execute()) {
            ResponseBody body = response.body();
            if (response.isSuccessful() && body != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode responseNode = objectMapper.readTree(body.string());
                imageUrl = responseNode.path("properties").path("presets").get(0).get("imageUrl").asText().replace("\"", "");
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }

        return imageUrl;
    }

    private Image fetchImageByURL(String imageUrl) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(imageUrl)
                .build();

        Image image = null;
        try (Response response = client.newCall(request).execute()) {
            ResponseBody body = response.body();
            if (response.isSuccessful() && body != null) {
                byte[] imageData = body.bytes();
                image = new Image(new java.io.ByteArrayInputStream(imageData));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}