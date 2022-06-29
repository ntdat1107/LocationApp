package com.example.locationapp.data.sources.local.preferlocations;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.locationapp.data.sources.model.preferlocation.Location;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

@Config(manifest = Config.NONE)
@RunWith(AndroidJUnit4.class)
public class PreferLocationsDatabaseTest {
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    private PreferLocationsDao preferLocationsDao;
    private PreferLocationsDatabase db;

    @Before
    public void createDatabase() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, PreferLocationsDatabase.class).allowMainThreadQueries().build();
        preferLocationsDao = db.locationDao();
    }

    @After
    public void closeDB() {
        db.close();
    }

    @Test
    public void insertTest() {
        List<Location> locationList = new ArrayList<>();
        Location location_1 = new Location("777d17bc-a5fa-4a06-8146-b7c6e7040b8f", "gem", "Gem Center", "http://gemcenter.com.vn/Images/img/gem_logo.png");
        locationList.add(location_1);


        for (Location location: locationList) {
            preferLocationsDao.insertLocation(location);
        }
        LiveData<List<Location>> livedata = preferLocationsDao.getAllPreferLocations();

        livedata.observeForever(locations -> {

        });

        assertEquals(livedata.getValue().get(0), location_1);
    }

    @Test
    public void insertDuplicated() {
        List<Location> locationList = new ArrayList<>();
        Location location_1 = new Location("777d17bc-a5fa-4a06-8146-b7c6e7040b8f", "gem", "Gem Center", "http://gemcenter.com.vn/Images/img/gem_logo.png");
        Location location_2 = new Location("777d17bc-a5fa-4a06-8146-b7c6e7040b8f", "bku", "BKU", "http://gemcenter.com.vn/Images/img/gem_logo.png");
        Location location_3 = new Location("777d17bc-a5fa-4a06-8146", "gem", "Gem Center", "http://gemcenter.com.vn/Images/img/gem_logo.png");
        locationList.add(location_1);
        locationList.add(location_2);
        locationList.add(location_3);


        for (Location location: locationList) {
            preferLocationsDao.insertLocation(location);
        }
        LiveData<List<Location>> livedata = preferLocationsDao.getAllPreferLocations();

        livedata.observeForever(locations -> {

        });

        assertEquals(livedata.getValue().size(), 2);
    }

}