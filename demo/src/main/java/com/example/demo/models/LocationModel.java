package com.example.demo.models;

import com.example.demo.models.Location;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class LocationModel {
    private ObservableList<Location> locationList;

    public LocationModel() {
        this.locationList = FXCollections.observableArrayList();
        this.locationList.addAll(
                new Location("Tampere", "23471", "C04507", "Tampere, Lakalaiva, Road 3", "23.769505", "61.462733", "458"),
                new Location("Helsinki", "23172", "C01650", "Helsinki, Landbo, Road 7", "25.209370", "60.266368", "101"),
                new Location("Rovaniemi", "24450", "C14547", "Rovaniemi, Revontuli, Road 4", "25.719219", "66.500292", "1405")
        );
    }

    public ObservableList<Location> getLocations() {
        return locationList;
    }
}
