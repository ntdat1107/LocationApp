package com.example.locationapp.data.repository;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.locationapp.data.sources.remote.LocationAPI;

import org.junit.Before;
import org.junit.Rule;
import org.mockito.Mockito;

public class LocationRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    LocationAPI locationAPI = Mockito.mock(LocationAPI.class);

    LocationRepository locationRepository;

    @Before
    public void setUp() {
        locationRepository = new LocationRepositoryImpl(locationAPI);
    }



}