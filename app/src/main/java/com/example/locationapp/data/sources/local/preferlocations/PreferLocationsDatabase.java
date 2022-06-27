package com.example.locationapp.data.sources.local.preferlocations;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.locationapp.data.sources.model.preferlocation.Location;

@Database(entities = {Location.class}, version = 1, exportSchema = false)
public abstract class PreferLocationsDatabase extends RoomDatabase {
    public abstract PreferLocationsDao locationDao();
}
