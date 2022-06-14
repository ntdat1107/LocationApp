package com.example.locationapp.domain.repository;

import com.example.locationapp.data.sources.remote.Location;

import java.util.List;

public interface LocationRepository {
    List<Location> getAllLocation();
}
