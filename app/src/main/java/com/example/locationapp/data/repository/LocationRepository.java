package com.example.locationapp.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.locationapp.data.sources.model.preferlocation.Root;
import com.example.locationapp.data.sources.model.detaillocation.RootDetail;
import com.example.locationapp.utils.Resource;

public interface LocationRepository {
    LiveData<Root> getAllLocation();

    LiveData<RootDetail> getLocationDetail(String locationID);
}
