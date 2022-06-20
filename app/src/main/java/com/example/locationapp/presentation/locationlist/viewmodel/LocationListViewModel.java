package com.example.locationapp.presentation.locationlist.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.locationapp.MyApplication;
import com.example.locationapp.data.sources.remote.model.Data;
import com.example.locationapp.data.sources.remote.model.Location;
import com.example.locationapp.data.sources.remote.model.Root;
import com.example.locationapp.domain.interactor.GetPreferLocationsUseCase;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationListViewModel extends ViewModel {
    @Inject
    GetPreferLocationsUseCase getPreferLocationsUseCase;

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


    public MutableLiveData<Data> getLocationsLiveData() {
        if (locationsLiveData == null) {
            locationsLiveData = new MutableLiveData<>();
        }
        return locationsLiveData;

    }

    public void fetchDataAPI() {
        getLoading().postValue(true);
        Call<Root> call = getPreferLocationsUseCase.execute();
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

    public void expandedItemView(int pos) {
        Data data = locationsLiveData.getValue();
        if (data == null) {
            return;
        }
        for (Location i : data.getLocations()) {
            i.setExpanded(null);
        }
        data.getLocations().get(pos).setExpanded(true);
        locationsLiveData.setValue(data);
    }
}
