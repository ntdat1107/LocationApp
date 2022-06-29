package com.example.locationapp.data.sources.local.location;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.locationapp.data.sources.model.detaillocation.LocationDetail;
import com.example.locationapp.utils.LiveDataTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.annotation.Config;

@Config(manifest = Config.NONE)
@RunWith(AndroidJUnit4.class)
public class LocationDatabaseTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    private LocationDao locationDao;
    private LocationDatabase db;

    @Before
    public void createDatabase() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, LocationDatabase.class).allowMainThreadQueries().build();
        locationDao = db.locationDao();
    }

    @After
    public void closeDB() {
        db.close();
    }

    @Test
    public void insertTest() {
        LocationDetail locationDetail = new LocationDetail("success", "gem", "GEM Center", "GEM Center.", 10, 106);
        locationDao.insertLocation(locationDetail);
        LiveData<LocationDetail> livedata = locationDao.getDetailLocations("success");

        livedata.observeForever(new Observer<LocationDetail>() {
            @Override
            public void onChanged(LocationDetail locationDetail) {

            }
        });

        assertEquals(livedata.getValue(), locationDetail);
    }

    @Test
    public void insertDuplicated() {
        LocationDetail locationDetailOld = new LocationDetail("success", "gem", "GEM Center", "GEM Center.", 10, 106);
        locationDao.insertLocation(locationDetailOld);
        LocationDetail locationDetailNew = new LocationDetail("success", "bku", "HCMUT", "DHBK HCM", 10, 106);
        locationDao.insertLocation(locationDetailNew);
        LiveData<LocationDetail> livedata = locationDao.getDetailLocations("success");

        livedata.observeForever(new Observer<LocationDetail>() {
            @Override
            public void onChanged(LocationDetail locationDetail) {

            }
        });

        assertEquals(livedata.getValue(), locationDetailNew);
    }
}
