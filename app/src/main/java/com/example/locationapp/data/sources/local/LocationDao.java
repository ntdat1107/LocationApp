package com.example.locationapp.data.sources.local;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.locationapp.data.sources.model.preferlocation.Location;

import java.util.List;

@Dao
public interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLocation(List<Location> location);

    @Query("SELECT * FROM prefer_locations")
    LiveData<List<Location>> getAllPreferLocations();


}
