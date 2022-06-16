package com.example.locationapp.presentation.locationdetail;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.locationapp.MyApplication;
import com.example.locationapp.domain.repository.LocationRepository;

import javax.inject.Inject;

public class LocationViewModel extends AndroidViewModel {
    @Inject
    LocationRepository locationRepository;



    LocationViewModel(Application application) {
        super(application);
        ((MyApplication) application).getLocationComponent().inject(this);
    }

    public void fetchLocationData() {
    }

}
