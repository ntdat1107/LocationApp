package com.example.locationapp.data.sources.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.locationapp.data.sources.model.detaillocation.LocationDetail;
import com.example.locationapp.data.sources.model.preferlocation.Location;

@Database(entities = {Location.class}, version = 1, exportSchema = false)
public abstract class LocationDatabase extends RoomDatabase {
    public abstract LocationDao locationDao();
}
