package com.example.locationapp.presentation.locationlist;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.locationapp.MyApplication;
import com.example.locationapp.data.sources.remote.model.Data;
import com.example.locationapp.data.sources.remote.model.Location;
import com.example.locationapp.data.sources.remote.model.Root;
import com.example.locationapp.domain.interactor.GetPreferLocationsUseCase;
import com.example.locationapp.domain.repository.LocationRepository;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationListViewModel extends AndroidViewModel {
    @Inject
    GetPreferLocationsUseCase getPreferLocationsUseCase;

    private MutableLiveData<Data> locationsLiveData;

    public LocationListViewModel(@NonNull Application application) {
        super(application);
        ((MyApplication) application).getLocationComponent().inject(this);
        fetchDataAPI();
    }

    public MutableLiveData<Data> getLocationsLiveData() {
        if (locationsLiveData == null) {
            locationsLiveData = new MutableLiveData<>();
        }
        return locationsLiveData;

    }

    public void fetchDataAPI() {
        Call<Root> call = getPreferLocationsUseCase.execute();
        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if (response.isSuccessful()) {
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

    public void expandedItemView(int pos) {
        Data data = locationsLiveData.getValue();
        if (data == null) {
            Toast.makeText(getApplication(), "Position is invalid", Toast.LENGTH_SHORT).show();
            return;
        }
        for (Location i: data.getLocations()) {
            i.setExpanded(null);
        }
        data.getLocations().get(pos).setExpanded(true);
        locationsLiveData.setValue(data);
    }
}
