package com.example.locationapp.data.repository;

import androidx.lifecycle.LiveData;

import com.example.locationapp.data.sources.model.preferlocation.Location;
import com.example.locationapp.utils.Resource;

import java.util.List;

public interface TestRepository {
    public LiveData<Resource<List<Location>>> getPreferLocations();
}
