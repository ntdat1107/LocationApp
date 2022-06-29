package com.example.locationapp.data.sources.local.location;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.locationapp.data.sources.model.detaillocation.LocationDetail;

import java.util.List;

@Dao
public interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLocation(LocationDetail locationDetail);

    @Query("SELECT * FROM location WHERE id = :locationID")
    LiveData<LocationDetail> getDetailLocations(String locationID);
}
