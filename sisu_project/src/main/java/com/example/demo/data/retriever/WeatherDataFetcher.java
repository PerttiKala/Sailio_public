package com.example.demo.data.retriever;

import com.example.demo.data.mappers.WeatherData;
import com.example.demo.models.WeatherDataModel;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class WeatherDataFetcher {

    private String baseUrl = "https://archive-api.open-meteo.com/v1/era5";
    private final OkHttpClient client;

    public WeatherDataFetcher() {
        this.client = new OkHttpClient();
    }

    private String fetchData(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            return response.body().string();
        }
    }

    public WeatherDataModel getData(String latitude, String longitude, LocalDate startDate, LocalDate endDate) throws IOException, JSONException {

        if (LocalDate.now().isBefore(startDate)) {
            startDate = LocalDate.now().minus(7, ChronoUnit.DAYS);
        }

        if (LocalDate.now().isBefore(endDate)) {
            endDate = LocalDate.now().minus(6, ChronoUnit.DAYS);
        }

        String rawData = this.fetchData(MessageFormat.format(
                this.baseUrl + "?latitude={0}&longitude={1}&start_date={2}&end_date={3}&hourly=temperature_2m",
                latitude,
                longitude,
                startDate,
                endDate
        ));


        JSONObject rawDataObj;
        if (rawData.charAt(0) == '[') {
            rawDataObj = new JSONObject(rawData.substring(1, rawData.length() - 2));
        } else {
            rawDataObj = new JSONObject(rawData);
        }

        String temperatureUnit = rawDataObj.getJSONObject("hourly_units").getString("temperature_2m");
        JSONArray timeData = rawDataObj.getJSONObject("hourly").getJSONArray("time");
        JSONArray temperatureData = rawDataObj.getJSONObject("hourly").getJSONArray("temperature_2m");

        // Collect temperature data to arrayList as WeatherData objects
        ArrayList<WeatherData> dataArray = new ArrayList<>();
        for (int i = 0; i < timeData.length(); i++) {
            if (temperatureData.get(i) == null) {
                break;
            }

            double temperature = temperatureData.getDouble(i);
            String timeStamp = (String) timeData.get(i);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(timeStamp, formatter);

            WeatherData weatherData = new WeatherData(dateTime, temperature);
            dataArray.add(weatherData);
        }
        return new WeatherDataModel(temperatureUnit, dataArray);
    }

}
