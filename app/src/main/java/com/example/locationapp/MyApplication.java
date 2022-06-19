package com.example.locationapp;

import android.app.Application;

import com.example.locationapp.di.DaggerLocationComponent;
import com.example.locationapp.di.LocationComponent;
import com.example.locationapp.di.LocationModule;

public class MyApplication extends Application {
    LocationComponent locationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        locationComponent = DaggerLocationComponent.builder()
            .locationModule(new LocationModule())
            .build();
    }

    public LocationComponent getLocationComponent() {
        return locationComponent;
    }
}
