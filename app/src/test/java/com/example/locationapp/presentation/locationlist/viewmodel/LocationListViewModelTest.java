package com.example.locationapp.presentation.locationlist.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.locationapp.data.repository.LocationRepository;
import com.example.locationapp.data.repository.LocationRepositoryImpl;
import com.example.locationapp.data.sources.model.preferlocation.Data;
import com.example.locationapp.data.sources.model.preferlocation.Location;
import com.example.locationapp.data.sources.model.preferlocation.Root;
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

        Root res = new Root(0, "", new Data(locationList));

        MutableLiveData<Resource<Root>> response = new MutableLiveData<>(new Resource.Success<>(res));

        Mockito.when(repository.getAllLocation()).thenReturn(response);

        viewModel.fetchDataAPI();

        Assert.assertFalse(viewModel.getLoading().getValue());
        Assert.assertEquals(viewModel.getLocationsLiveData().getValue(), res.getData());
    }

    @Test
    public void testFailAPI() {
        List<Location> locationList = new ArrayList<>();

        Root res = new Root(666, "Server error", new Data(locationList));

        MutableLiveData<Resource<Root>> response = new MutableLiveData<>(new Resource.Error<>(res, null));

        Mockito.when(repository.getAllLocation()).thenReturn(response);

        viewModel.fetchDataAPI();

        Assert.assertFalse(viewModel.getLoading().getValue());
        Assert.assertEquals(viewModel.getError_message().getValue(), res.getError_message());
    }
}