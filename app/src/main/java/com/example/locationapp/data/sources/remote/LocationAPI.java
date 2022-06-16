package com.example.locationapp.data.sources.remote;

import com.example.locationapp.data.sources.remote.model.Root;
import com.example.locationapp.data.sources.remote.model.RootDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LocationAPI {

    @GET("/v3/2fe37bd6-9dd0-4384-9a65-14ae709b82d9")
    Call<Root> fetchAllLocation();

    @GET("/v3/{id}")
    Call<RootDetail> fetchLocationDetail(@Path("id") String locationID);
}
