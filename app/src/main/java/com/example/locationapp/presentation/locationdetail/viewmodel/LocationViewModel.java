package com.example.locationapp.presentation.locationdetail.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.locationapp.data.repository.LocationRepository;
import com.example.locationapp.data.sources.model.detaillocation.RootDetail;
import com.example.locationapp.utils.Resource;
import com.example.locationapp.utils.Status;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

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

        MutableLiveData<Resource<RootDetail>> response = locationRepository.getLocationDetail(locationID);

        response.observeForever(new Observer<Resource<RootDetail>>() {
            @Override
            public void onChanged(Resource<RootDetail> rootResource) {
                if (rootResource.getStatus() == Status.SUCCESS) {
                    getLoading().setValue(false);
                    getLocationDetailMutableLiveData().setValue(rootResource.getData());
                } else if (rootResource.getStatus() == Status.ERROR) {
                    getLoading().setValue(false);
                    getError_message().setValue(rootResource.getData().getError_message());
                }
            }
        });



//        call.enqueue(new Callback<RootDetail>() {
//            @Override
//            public void onResponse(@NonNull Call<RootDetail> call, @NonNull Response<RootDetail> response) {
//                if (response.isSuccessful()) {
//                    if (response.body() == null) {
//                        getError_message().setValue("Empty data");
//                    } else if (response.body().getError_code() != 0) {
//                        getError_message().setValue(response.body().getError_message());
//                    } else {
//                        getLocationDetailMutableLiveData().setValue(response.body());
//                        getError_message().setValue(null);
//                    }
//                } else {
//                    getError_message().setValue(response.message());
//                }
//                loading.setValue(false);
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<RootDetail> call, @NonNull Throwable t) {
//                getError_message().setValue(t.getMessage());
//                loading.setValue(false);
//            }
//        });
    }

}
