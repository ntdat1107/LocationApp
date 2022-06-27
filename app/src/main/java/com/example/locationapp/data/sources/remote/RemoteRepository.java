package com.example.locationapp.data.sources.remote;

import androidx.lifecycle.MutableLiveData;

import com.example.locationapp.data.sources.model.preferlocation.Root;
import com.example.locationapp.data.sources.model.detaillocation.RootDetail;
import com.example.locationapp.utils.Resource;

import retrofit2.Call;

public interface RemoteRepository {
    Call<Root> getAllLocation();

    Call<RootDetail> getLocationDetail(String locationID);
}
