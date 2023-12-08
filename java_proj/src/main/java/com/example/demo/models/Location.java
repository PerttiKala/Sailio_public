package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {
    @JsonProperty("locationName")
    private final String locationName;
    @JsonProperty("stationId")
    private final String stationId;
    @JsonProperty("tmsPointId")
    private final String tmsPointId;
    @JsonProperty("weatherStationId")
    private final String weatherStationId;
    @JsonProperty("weatherStationName")
    private final String weatherStationName;
    @JsonProperty("longitude")
    private final String longitude;
    @JsonProperty("latitude")
    private final String latitude;


    @JsonCreator
    public Location(    @JsonProperty("locationName") String locationName,     @JsonProperty("stationId")String stationId,   @JsonProperty("weatherStationId") String weatherStationId, @JsonProperty("weatherStationName") String weatherStationName,@JsonProperty("longitude") String longitude,@JsonProperty("latitude") String latitude,@JsonProperty("tmsPointId") String tmsPointId) {
        this.locationName = locationName;
        this.stationId = stationId;
        this.weatherStationId = weatherStationId;
        this.weatherStationName = weatherStationName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.tmsPointId = tmsPointId;
    }

    public String getLocationName(){
        return locationName;
    }

    public String getStationId() {
        return stationId;
    }

    public String getWeatherStationId() {
        return weatherStationId;
    }

    public String getWeatherStationName() {
        return weatherStationName;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getTmsPointId() { return tmsPointId; }

    @Override
    public String toString() {
        return this.locationName;
    }
}
