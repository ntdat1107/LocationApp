package com.example.locationapp.presentation.locationdetail;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.locationapp.MyApplication;
import com.example.locationapp.data.sources.remote.model.LocationDetail;
import com.example.locationapp.data.sources.remote.model.Root;
import com.example.locationapp.data.sources.remote.model.RootDetail;
import com.example.locationapp.domain.repository.LocationRepository;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationViewModel extends AndroidViewModel {
    @Inject
    LocationRepository locationRepository;

    private MutableLiveData<RootDetail> locationDetailMutableLiveData;

    public MutableLiveData<RootDetail> getLocationDetailMutableLiveData() {
        if (locationDetailMutableLiveData == null) {
            locationDetailMutableLiveData = new MutableLiveData<RootDetail>();
        }
        return locationDetailMutableLiveData;
    }

    public LocationViewModel(@NonNull Application application) {
        super(application);
        ((MyApplication) application).getLocationComponent().inject(this);
    }

    public void fetchLocationData(String locationID) {
        Call<RootDetail> call = locationRepository.getLocationDetail(locationID);

        call.enqueue(new Callback<RootDetail>() {
            @Override
            public void onResponse(Call<RootDetail> call, Response<RootDetail> response) {
                if (response.isSuccessful()) {
                    locationDetailMutableLiveData.setValue(response.body());
                } else {
                    locationDetailMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<RootDetail> call, Throwable t) {

            }
        });
    }

}
