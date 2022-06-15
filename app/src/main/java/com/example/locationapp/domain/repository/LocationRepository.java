package com.example.locationapp.domain.repository;

import com.example.locationapp.data.sources.remote.Location;
import com.example.locationapp.data.sources.remote.Root;

import java.util.List;

import retrofit2.Call;

public interface LocationRepository {
    Call<Root> getAllLocation();
}
