package com.example.demo.services;

import com.example.demo.controllers.JsonReaderController;
import com.example.demo.models.Location;
import com.example.demo.models.LocationModel;
import com.example.demo.models.UserPreferenceModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Objects;

public class LocationService {
    public LocationService() {
        UserPreferenceModel data = new JsonReaderController().readJsonData();
        if (data != null && Objects.nonNull(data.getLocation())) {
            this.selectedLocation = new Location(data.getLocation().getLocationName() , data.getLocation().getStationId(), data.getLocation().getWeatherStationId(),data.getLocation().getWeatherStationName(),data.getLocation().getLongitude(), data.getLocation().getLatitude(),data.getLocation().getTmsPointId());
        }else {
            this.selectedLocation = new Location("Helsinki" , "23471", "C04507", "Helsinki","60,1699", "24,9384","439");
        }
    }

    public Location getSelectedLocation() {
        return selectedLocation;
    }

    public LocationModel getLocationModel() {
        return locationModel;
    }

    public Location selectedLocation;
    private LocationModel locationModel = new LocationModel();

    private static final LocationService instance = new LocationService();

    public static LocationService getInstance() { return instance; }
    
    public ObservableList<Location> getLocations() {
        return locationModel.getLocations();
    }

    public Location getDefaultLocation() {
        return selectedLocation;
    }

    public void passSelectedLocation(Location location) { this.selectedLocation = location; }
}
