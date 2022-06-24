package com.example.locationapp.presentation.locationdetail.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.locationapp.data.repository.LocationRepository;
import com.example.locationapp.data.repository.LocationRepositoryImpl;
import com.example.locationapp.data.sources.model.detaillocation.DataDetail;
import com.example.locationapp.data.sources.model.detaillocation.LocationDetail;
import com.example.locationapp.data.sources.model.detaillocation.RootDetail;
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
        LocationDetail locationDetail = new LocationDetail("hcmut", "Bach Khoa HCM", "Nope", 10, 20);
        RootDetail rootDetail = new RootDetail(0, "", new DataDetail(locationDetail));

        MutableLiveData<Resource<RootDetail>> response = new MutableLiveData<>(new Resource.Success<>(rootDetail));

        Mockito.when(repository.getLocationDetail("success")).thenReturn(response);

        viewModel.fetchLocationData("success");

        Assert.assertEquals(rootDetail, viewModel.getLocationDetailMutableLiveData().getValue());
        Assert.assertFalse(viewModel.getLoading().getValue());
    }

    @Test
    public void testFail() {
        RootDetail rootDetail = new RootDetail(666, "Unknown Location", null);

        MutableLiveData<Resource<RootDetail>> response = new MutableLiveData<>(new Resource.Error<>(rootDetail, null));

        Mockito.when(repository.getLocationDetail("fail")).thenReturn(response);

        viewModel.fetchLocationData("fail");

        Assert.assertNull(viewModel.getLocationDetailMutableLiveData().getValue());
        Assert.assertEquals("Unknown Location", viewModel.getError_message().getValue());
        Assert.assertFalse(viewModel.getLoading().getValue());
    }

}