package com.example.locationapp.presentation.locationlist.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.locationapp.data.remote.LocationRepository;
import com.example.locationapp.data.remote.LocationRepositoryImpl;
import com.example.locationapp.data.sources.model.preferlocation.Location;
import com.example.locationapp.utils.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class LocationListViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    LocationRepository repository = Mockito.mock(LocationRepositoryImpl.class);

    LocationListViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = Mockito.spy(new LocationListViewModel(repository));
    }

    @Test
    public void testSuccessAPI() {
        List<Location> locationList = new ArrayList<>();
        locationList.add(new Location("777d17bc-a5fa-4a06-8146-b7c6e7040b8f", "gem", "Gem Center", "http://gemcenter.com.vn/Images/img/gem_logo.png"));
        Resource<List<Location>> response = new Resource.Success<>(locationList);

        MutableLiveData<Resource<List<Location>>> liveData = new MutableLiveData<>();
        liveData.setValue(response);

        Mockito.when(repository.getPreferLocations()).thenReturn(liveData);

        viewModel.fetchDataAPI();

        Assert.assertFalse(viewModel.getLoading().getValue());
        Assert.assertEquals(viewModel.getLocationsLiveData().getValue(), locationList);
    }

    @Test
    public void testFailAPI() {
        List<Location> locationList = new ArrayList<>();
        locationList.add(new Location("777d17bc-a5fa-4a06-8146-b7c6e7040b8f", "gem", "Gem Center", "http://gemcenter.com.vn/Images/img/gem_logo.png"));
        Resource<List<Location>> response = new Resource.Error<>(locationList, "Server error");

        MutableLiveData<Resource<List<Location>>> liveData = new MutableLiveData<>();
        liveData.setValue(response);

        Mockito.when(repository.getPreferLocations()).thenReturn(liveData);

        viewModel.fetchDataAPI();

        Assert.assertFalse(viewModel.getLoading().getValue());
        Assert.assertEquals(viewModel.getError_message().getValue(), response.getError());
    }
}