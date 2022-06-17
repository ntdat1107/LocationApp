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
    private MutableLiveData<Boolean> loading;
    private MutableLiveData<String> error_message;

    public MutableLiveData<Boolean> getLoading() {
        if (loading == null) {
            loading = new MutableLiveData<Boolean>();
        }
        return loading;
    }

    public MutableLiveData<String> getError_message() {
        if (error_message == null) {
            error_message = new MutableLiveData<String>();
        }
        return error_message;
    }

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
        getLoading().postValue(true);
        Call<RootDetail> call = locationRepository.getLocationDetail(locationID);

        call.enqueue(new Callback<RootDetail>() {
            @Override
            public void onResponse(@NonNull Call<RootDetail> call, @NonNull Response<RootDetail> response) {
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        getError_message().setValue("Empty data");
                    } else if (response.body().getError_code() != 0) {
                        getError_message().setValue(response.body().getError_message());
                    } else {
                        locationDetailMutableLiveData.setValue(response.body());
                    }
                } else {
                    getError_message().setValue(response.message());
                }
                loading.setValue(false);
            }

            @Override
            public void onFailure(@NonNull Call<RootDetail> call, @NonNull Throwable t) {
                getError_message().setValue(t.getMessage());
                loading.setValue(false);
            }
        });
    }

}
