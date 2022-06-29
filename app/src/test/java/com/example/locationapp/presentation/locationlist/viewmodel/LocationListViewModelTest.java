package com.example.locationapp.presentation.locationlist.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

<<<<<<< HEAD
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import com.example.locationapp.DaggerTestLocationComponent;
import com.example.locationapp.TestLocationComponent;
import com.example.locationapp.TestLocationModule;
import com.example.locationapp.data.sources.remote.model.Root;
=======
import com.example.locationapp.data.repository.LocationRepository;
import com.example.locationapp.data.repository.LocationRepositoryImpl;
import com.example.locationapp.data.sources.model.preferlocation.Location;
import com.example.locationapp.utils.Resource;
>>>>>>> Final

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

<<<<<<< HEAD
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RunWith(MockitoJUnitRunner.class)
public class LocationListViewModelTest {
=======
import java.util.ArrayList;
import java.util.List;

public class LocationListViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    LocationRepository repository = Mockito.mock(LocationRepositoryImpl.class);
>>>>>>> Final

    LocationListViewModel viewModel;

    @Before
    public void setUp() {
<<<<<<< HEAD
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
=======
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
>>>>>>> Final
    }
}