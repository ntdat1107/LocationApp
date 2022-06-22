package com.example.locationapp.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.locationapp.data.sources.remote.model.preferlocation.Root;
import com.example.locationapp.data.sources.remote.model.detaillocation.RootDetail;
import com.example.locationapp.utils.Resource;

public interface LocationRepository {
    MutableLiveData<Resource<Root>> getAllLocation();

    MutableLiveData<Resource<RootDetail>> getLocationDetail(String locationID);
}
