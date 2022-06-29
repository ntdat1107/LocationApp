package com.example.locationapp.presentation.locationlist.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.locationapp.data.repository.LocationRepository;
import com.example.locationapp.data.sources.model.preferlocation.Location;
import com.example.locationapp.utils.Resource;
import com.example.locationapp.utils.Status;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LocationListViewModel extends ViewModel {
    public final LocationRepository locationRepository;

    private MutableLiveData<List<Location>> locationsLiveData;

    private MutableLiveData<Boolean> loading;

    private MutableLiveData<String> error_message;

    LiveData<Resource<List<Location>>> liveData;


    @Inject
    public LocationListViewModel(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

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

    public MutableLiveData<List<Location>> getLocationsLiveData() {
        if (locationsLiveData == null) {
            locationsLiveData = new MutableLiveData<List<Location>>();
        }
        return locationsLiveData;
    }

    public void fetchDataAPI() {
        getLoading().postValue(true);
        getError_message().postValue(null);
        getLocationsLiveData().postValue(null);

        liveData = locationRepository.getPreferLocations();

        liveData.observeForever(new Observer<Resource<List<Location>>>() {
            @Override
            public void onChanged(Resource<List<Location>> listResource) {
                if (listResource.getStatus() == Status.SUCCESS) {
                    getLoading().setValue(false);
                    if (listResource.getData() != null) {
                        getLocationsLiveData().setValue(listResource.getData());
                    } else {
                        getError_message().setValue("No data");
                    }
                } else if (listResource.getStatus() == Status.ERROR) {
                    getLoading().setValue(false);
                    getError_message().setValue(listResource.getError());
                }
            }
        });

    }
}
