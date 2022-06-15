package com.example.locationapp.presentation;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.locationapp.MyApplication;
import com.example.locationapp.data.repository.LocationRepositoryImpl;
import com.example.locationapp.data.sources.remote.Data;
import com.example.locationapp.data.sources.remote.Location;
import com.example.locationapp.data.sources.remote.Root;
import com.example.locationapp.domain.repository.LocationRepository;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationListViewModel extends AndroidViewModel {
    @Inject
    LocationRepository locationRepository;

    private MutableLiveData<Data> locationsLiveData;

    public LocationListViewModel(@NonNull Application application) {
        super(application);
        ((MyApplication) application).getLocationComponent().inject(this);
        locationsLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Data> getLocationsLiveData() {
        return locationsLiveData;
    }

    public void fetchDataAPI() {
        Call<Root> call = locationRepository.getAllLocation();
        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if (response.isSuccessful()) {
                    Log.i("test", response.body().getData().getLocations().get(0).getImage().toString());
                    locationsLiveData.setValue(response.body().getData());
                } else {
                    locationsLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                locationsLiveData.postValue(null);
            }
        });
    }
}
