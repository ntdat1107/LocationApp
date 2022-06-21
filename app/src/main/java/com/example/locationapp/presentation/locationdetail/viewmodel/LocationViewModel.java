package com.example.locationapp.presentation.locationdetail.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.locationapp.data.sources.remote.model.RootDetail;
import com.example.locationapp.data.repository.LocationRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class LocationViewModel extends ViewModel {

    public final LocationRepository locationRepository;
    private MutableLiveData<RootDetail> locationDetailMutableLiveData;
    private MutableLiveData<Boolean> loading;
    private MutableLiveData<String> error_message;

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

    public MutableLiveData<RootDetail> getLocationDetailMutableLiveData() {
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
                        getLocationDetailMutableLiveData().setValue(response.body());
                        getError_message().setValue(null);
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
