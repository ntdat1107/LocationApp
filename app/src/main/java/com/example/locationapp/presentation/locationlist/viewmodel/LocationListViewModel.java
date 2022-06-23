package com.example.locationapp.presentation.locationlist.viewmodel;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.locationapp.data.repository.LocationRepository;
import com.example.locationapp.data.sources.remote.model.preferlocation.Data;
import com.example.locationapp.data.sources.remote.model.preferlocation.Root;
import com.example.locationapp.utils.Resource;
import com.example.locationapp.utils.Status;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LocationListViewModel extends ViewModel {
    public final LocationRepository locationRepository;

    private MutableLiveData<Data> locationsLiveData;

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

    @Inject
    public LocationListViewModel(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public MutableLiveData<Data> getLocationsLiveData() {
        if (locationsLiveData == null) {
            locationsLiveData = new MutableLiveData<Data>();
        }
        return locationsLiveData;

    }

    public void fetchDataAPI() {
        getLoading().postValue(true);
        getError_message().postValue(null);
        getLocationsLiveData().postValue(null);

        MutableLiveData<Resource<Root>> response = locationRepository.getAllLocation();

        response.observeForever(new Observer<Resource<Root>>() {
            @Override
            public void onChanged(Resource<Root> rootResource) {
                if (rootResource.getStatus() == Status.SUCCESS) {
                    getLoading().setValue(false);
                    getLocationsLiveData().setValue(rootResource.getData().getData());
                } else if (rootResource.getStatus() == Status.ERROR) {
                    getLoading().setValue(false);
                    getError_message().setValue(rootResource.getError().getMessage());
                }
            }
        });

//        private MutableLiveData<String> processInput = new MutableLiveData<>();
//        public final LiveData<String> formatJson = Transformations
//                .switchMap(Transformations.distinctUntilChanged(processInput),
//                        new Function<String, LiveData<String>>() {
//                            @Override
//                            public LiveData<String> apply(String input) {
//                                return Transformations.map(repo.getJson(input), output -> {
//                                    if(output.getStatus() != Status.SUCCESS) {
//                                        return context.getString(R.string.process_user_comments) ;
//                                    } else {
//                                        return output.getData();
//                                    }
//                                });
//                            }
//                        });
//
//
//        public void requestJsonFromInput(String input) {
//            processInput.setValue(input);
//        }

    }
}
