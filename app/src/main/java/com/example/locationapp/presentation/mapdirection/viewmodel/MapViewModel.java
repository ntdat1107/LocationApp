package com.example.locationapp.presentation.mapdirection.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.locationapp.data.sources.model.detaillocation.LocationDetail;
import com.example.locationapp.utils.Resource;

public class MapViewModel extends ViewModel {
    private MutableLiveData<Boolean> loading;
    private MutableLiveData<String> error_message;
    private MutableLiveData<LocationDetail> locationDetailLiveData;

    public MutableLiveData<Boolean> getLoading() {
        if (loading == null) {
            loading = new MutableLiveData<>();
        }
        return loading;
    }

    public MutableLiveData<String> getError_message() {
        if (error_message == null) {
            error_message = new MutableLiveData<>();
        }
        return error_message;
    }

    public MutableLiveData<LocationDetail> getLocationDetailMutableLiveData() {
        if (locationDetailLiveData == null) {
            locationDetailLiveData = new MutableLiveData<>();
        }
        return locationDetailLiveData;
    }
}
