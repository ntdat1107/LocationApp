package com.example.locationapp.presentation.locationdetail.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.locationapp.data.repository.LocationRepository;
import com.example.locationapp.data.sources.model.detaillocation.LocationDetail;
import com.example.locationapp.utils.Resource;
import com.example.locationapp.utils.Status;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LocationViewModel extends ViewModel {

    public final LocationRepository locationRepository;
    private MutableLiveData<LocationDetail> locationDetailMutableLiveData;
    private MutableLiveData<Boolean> loading;
    private MutableLiveData<String> error_message;
    private LiveData<Resource<LocationDetail>> liveData;


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
        if (locationDetailMutableLiveData == null) {
            locationDetailMutableLiveData = new MutableLiveData<>();
        }
        return locationDetailMutableLiveData;
    }

    @Inject
    public LocationViewModel(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public void fetchLocationData(String locationID) {
        getLoading().postValue(true);
        getError_message().postValue(null);
        getLocationDetailMutableLiveData().postValue(null);

        liveData = locationRepository.getDetailLocation(locationID);

        liveData.observeForever(new Observer<Resource<LocationDetail>>() {
            @Override
            public void onChanged(Resource<LocationDetail> locationDetailResource) {
                if (locationDetailResource.getStatus() == Status.SUCCESS) {
                    getLoading().setValue(false);
                    if (locationDetailResource.getData() != null) {
                        getLocationDetailMutableLiveData().setValue(locationDetailResource.getData());
                    } else {
                        getError_message().setValue("No data");
                    }
                } else if (locationDetailResource.getStatus() == Status.ERROR) {
                    getLoading().setValue(false);
                    getError_message().setValue(locationDetailResource.getError());
                }
            }
        });
    }

}
