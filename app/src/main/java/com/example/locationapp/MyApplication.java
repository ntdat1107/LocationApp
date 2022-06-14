package com.example.locationapp;

import android.app.Application;

import com.example.locationapp.di.DaggerLocationComponent;
import com.example.locationapp.di.LocationComponent;

public class MyApplication extends Application {
    LocationComponent component = DaggerLocationComponent.create();
}
