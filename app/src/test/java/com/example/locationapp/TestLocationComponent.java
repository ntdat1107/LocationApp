package com.example.locationapp;

import com.example.locationapp.di.LocationComponent;
import com.example.locationapp.di.LocationModule;
import com.example.locationapp.presentation.locationlist.viewmodel.LocationListViewModelTest;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {LocationModule.class})
public interface TestLocationComponent extends LocationComponent {
    void inject(LocationListViewModelTest locationListViewModelTest);
}
