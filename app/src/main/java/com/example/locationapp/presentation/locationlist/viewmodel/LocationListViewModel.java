package com.example.locationapp.presentation.locationlist.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.locationapp.data.sources.remote.model.Data;
import com.example.locationapp.data.sources.remote.model.Root;
import com.example.locationapp.data.repository.LocationRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class LocationListViewModel extends ViewModel {
    public final LocationRepository locationRepository;

    private MutableLiveData<Data> locationsLiveData;

    private MutableLiveData<Boolean> loading;

    private MutableLiveData<String> error_msg;

    public MutableLiveData<Boolean> getLoading() {
        if (loading == null) {
            loading = new MutableLiveData<>();
        }
        return loading;
    }

    public MutableLiveData<String> getError_msg() {
        if (error_msg == null) {
            error_msg = new MutableLiveData<>();
        }
        return error_msg;
    }

    @Inject
    public LocationListViewModel(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public MutableLiveData<Data> getLocationsLiveData() {
        if (locationsLiveData == null) {
            locationsLiveData = new MutableLiveData<>();
        }
        return locationsLiveData;

    }

    public void fetchDataAPI() {
        getLoading().postValue(true);
        Call<Root> call = locationRepository.getAllLocation();
        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(@NonNull Call<Root> call, @NonNull Response<Root> response) {
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        getError_msg().setValue("Empty data");
                    } else if (response.body().getError_code() != 0) {
                        getError_msg().setValue(response.body().getError_message());
                    } else {
                        getLocationsLiveData().setValue(response.body().getData());
                    }
                } else {
                    getError_msg().setValue(response.message());
                }
                loading.setValue(false);
            }

            @Override
            public void onFailure(@NonNull Call<Root> call, @NonNull Throwable t) {
                loading.setValue(false);
                getError_msg().setValue(t.getMessage());
            }
        });
    }
}
