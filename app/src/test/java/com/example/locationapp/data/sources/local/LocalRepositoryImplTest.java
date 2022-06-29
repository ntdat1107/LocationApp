package com.example.locationapp.data.sources.local;

import static org.junit.Assert.*;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.locationapp.data.sources.local.location.LocationDatabase;
import com.example.locationapp.data.sources.local.preferlocations.PreferLocationsDatabase;
import com.example.locationapp.data.sources.remote.LocationAPI;

import org.junit.Before;
import org.junit.Rule;

import java.io.IOException;

public class LocalRepositoryImplTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    private PreferLocationsDatabase preferLocationsDatabase;
    private LocationDatabase locationDatabase;

    @Before
    public void setUp() {

    }
}