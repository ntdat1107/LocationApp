package com.example.locationapp.presentation.locationlist.viewmodel;


import com.example.locationapp.DaggerTestLocationComponent;
import com.example.locationapp.TestLocationComponent;
import com.example.locationapp.TestLocationModule;
import com.example.locationapp.data.sources.remote.model.Data;
import com.example.locationapp.data.sources.remote.model.Location;
import com.example.locationapp.data.sources.remote.model.Root;
import com.example.locationapp.domain.interactor.GetPreferLocationsUseCase;

import static org.junit.Assert.*;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RunWith(MockitoJUnitRunner.class)
public class LocationListViewModelTest {
    @Inject
    GetPreferLocationsUseCase getPreferLocationsUseCase;

    LocationListViewModel viewModel;

    @Before
    public void setUp() {
//        TestLocationComponent testLocationComponent = DaggerTestLocationComponent.builder().locationModule(new TestLocationModule())
//                .build();
//        testLocationComponent.inject(this);
        Application application = ApplicationProvider.getApplicationContext();
        viewModel = new ViewModelProvider((ViewModelStoreOwner) application).get(LocationListViewModel.class);
    }

    @Test
    public void doSomething() {
    }
}