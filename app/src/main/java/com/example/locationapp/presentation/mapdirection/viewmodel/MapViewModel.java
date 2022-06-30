package com.example.locationapp.presentation.mapdirection.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.locationapp.data.sources.model.detaillocation.LocationDetail;

public class MapViewModel extends ViewModel {
    private MutableLiveData<LocationDetail> locationDetailLiveData;

    public MutableLiveData<LocationDetail> getLocationDetailMutableLiveData() {
        if (locationDetailLiveData == null) {
            locationDetailLiveData = new MutableLiveData<>();
        }
        return locationDetailLiveData;
    }
}
