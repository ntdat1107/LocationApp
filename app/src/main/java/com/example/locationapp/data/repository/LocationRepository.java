package com.example.locationapp.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.locationapp.data.sources.model.preferlocation.Root;
import com.example.locationapp.data.sources.model.detaillocation.RootDetail;
import com.example.locationapp.utils.Resource;

import retrofit2.Call;

public interface LocationRepository {
    Call<Root> getAllLocation();

    Call<RootDetail> getLocationDetail(String locationID);
}
