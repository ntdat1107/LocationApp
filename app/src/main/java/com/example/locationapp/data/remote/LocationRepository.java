package com.example.locationapp.data.remote;

import androidx.lifecycle.LiveData;

import com.example.locationapp.data.sources.model.detaillocation.LocationDetail;
import com.example.locationapp.data.sources.model.preferlocation.Location;
import com.example.locationapp.utils.Resource;

import java.util.List;

public interface LocationRepository {
    public LiveData<Resource<List<Location>>> getPreferLocations();

    public LiveData<Resource<LocationDetail>> getDetailLocation(String locationID);
}
