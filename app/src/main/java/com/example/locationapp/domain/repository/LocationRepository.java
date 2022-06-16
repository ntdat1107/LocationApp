package com.example.locationapp.domain.repository;

import com.example.locationapp.data.sources.remote.model.Root;
import com.example.locationapp.data.sources.remote.model.RootDetail;

import retrofit2.Call;

public interface LocationRepository {
    Call<Root> getAllLocation();

    Call<RootDetail> getLocationDetail(String locationID);
}
