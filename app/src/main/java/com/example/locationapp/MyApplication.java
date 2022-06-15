package com.example.locationapp;

import android.app.Application;

import com.example.locationapp.di.AppModule;
import com.example.locationapp.di.DaggerLocationComponent;
import com.example.locationapp.di.LocationComponent;

public class MyApplication extends Application {
    LocationComponent locationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        locationComponent = DaggerLocationComponent.builder()
                .appModule(new AppModule())
                .build();
    }

    public LocationComponent getLocationComponent() {
        return locationComponent;
    }
}
