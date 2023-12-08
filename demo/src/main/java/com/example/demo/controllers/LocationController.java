package com.example.demo.controllers;

import com.example.demo.models.LocationModel;
import com.example.demo.services.LocationService;
import com.example.demo.models.Location;
import com.example.demo.views.LocationView;
import javafx.collections.ObservableList;

public class LocationController {
    public LocationService locationService = LocationService.getInstance();

    public ObservableList<Location> getLocations() {
        return locationService.getLocations();
    }

    public Location getDefaultLocation() {
        Location defaultLocation = locationService.getDefaultLocation();
        if (defaultLocation == null) {
            defaultLocation = getLocations().get(0);
        }
        return defaultLocation;
    }
    public Location getSelectedLocation() {
        return locationService.getSelectedLocation();
    }

    public void passSelectedLocation(Location location) {locationService.passSelectedLocation(location);}
}
