package com.example.locationapp.presentation.locationlist.viewmodel;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import com.example.locationapp.DaggerTestLocationComponent;
import com.example.locationapp.TestLocationComponent;
import com.example.locationapp.TestLocationModule;
import com.example.locationapp.data.sources.remote.model.Root;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RunWith(MockitoJUnitRunner.class)
public class LocationListViewModelTest {

    LocationListViewModel viewModel;

    @Before
    public void setUp() {
        TestLocationComponent testLocationComponent = DaggerTestLocationComponent.builder().locationModule(new TestLocationModule())
                .build();
        viewModel = new LocationListViewModel();
        testLocationComponent.inject(viewModel);
    }

    @Test
    public void doSomething() {
        assertNull(viewModel.getLoading().getValue());
        viewModel.getPreferLocationsUseCase.execute().enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                assertEquals(viewModel.getLoading().getValue(), false);
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                assertNotEquals(viewModel.getError_msg().getValue(), null);
            }
        });

    }
    @Test
    public void doIt() {
        assertEquals(1+1,2);
    }
}