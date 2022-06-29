package com.example.locationapp.presentation.locationdetail.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.locationapp.data.repository.LocationRepository;
import com.example.locationapp.data.repository.LocationRepositoryImpl;
import com.example.locationapp.data.sources.model.detaillocation.LocationDetail;
import com.example.locationapp.utils.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

public class LocationViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    LocationRepository repository = Mockito.mock(LocationRepositoryImpl.class);

    LocationViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = Mockito.spy(new LocationViewModel(repository));
    }

    @Test
    public void testSuccess() {
        LocationDetail locationDetail = new LocationDetail("abcdef", "hcmut", "Bach Khoa HCM", "Nope", 10, 20);

        MutableLiveData<Resource<LocationDetail>> response = new MutableLiveData<>(new Resource.Success<>(locationDetail));

        Mockito.when(repository.getDetailLocation("success")).thenReturn(response);

        viewModel.fetchLocationData("success");

        Assert.assertEquals(locationDetail, viewModel.getLocationDetailMutableLiveData().getValue());
        Assert.assertFalse(viewModel.getLoading().getValue());
    }

    @Test
    public void testFail() {
        LocationDetail locationDetail = new LocationDetail("abcdef", "hcmut", "Bach Khoa HCM", "Nope", 10, 20);

        MutableLiveData<Resource<LocationDetail>> response = new MutableLiveData<>(new Resource.Error<>(locationDetail, "Unknown Location"));

        Mockito.when(repository.getDetailLocation("fail")).thenReturn(response);

        viewModel.fetchLocationData("fail");

        Assert.assertNull(viewModel.getLocationDetailMutableLiveData().getValue());
        Assert.assertEquals("Unknown Location", viewModel.getError_message().getValue());
        Assert.assertFalse(viewModel.getLoading().getValue());
    }

}