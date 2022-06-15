package com.example.locationapp.data.sources.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LocationAPI {

    @GET("/v3/2fe37bd6-9dd0-4384-9a65-14ae709b82d9")
    Call<Root> fetchAllLocation();
}
