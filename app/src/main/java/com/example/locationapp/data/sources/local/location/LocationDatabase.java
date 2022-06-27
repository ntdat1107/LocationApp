package com.example.locationapp.data.sources.local.location;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.locationapp.data.sources.model.detaillocation.LocationDetail;

@Database(entities = {LocationDetail.class}, version = 1, exportSchema = false)
public abstract class LocationDatabase extends RoomDatabase {
    public abstract LocationDao locationDao();
}
