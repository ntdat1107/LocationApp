package com.example.locationapp;

import com.example.locationapp.di.LocationComponent;

public class TestMyApplication extends MyApplication {
    TestLocationComponent testLocationComponent;

    @Override
    public LocationComponent getLocationComponent() {
        return testLocationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        testLocationComponent = DaggerTestLocationComponent.builder().locationModule(new TestLocationModule()).build();
    }
}
