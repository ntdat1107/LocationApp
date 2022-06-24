package com.example.locationapp.data.repository;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.locationapp.data.sources.remote.LocationAPI;
import com.example.locationapp.data.sources.model.detaillocation.DataDetail;
import com.example.locationapp.data.sources.model.detaillocation.LocationDetail;
import com.example.locationapp.data.sources.model.detaillocation.RootDetail;
import com.example.locationapp.data.sources.model.preferlocation.Data;
import com.example.locationapp.data.sources.model.preferlocation.Location;
import com.example.locationapp.data.sources.model.preferlocation.Root;
import com.example.locationapp.utils.Resource;
import com.example.locationapp.utils.Status;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mockwebserver3.Dispatcher;
import mockwebserver3.MockResponse;
import mockwebserver3.MockWebServer;
import mockwebserver3.RecordedRequest;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    LocationAPI locationAPI;


    MockWebServer mockWebServer;

    LocationRepository locationRepository;

    @Before
    public void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start(8080);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(mockWebServer.url("").toString())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        locationAPI = retrofit.create(LocationAPI.class);

        locationRepository = new LocationRepositoryImpl(locationAPI);
    }

    // 3 tests for fetchAllLocation
    @Test
    public void testFetchPreferLocationSuccess() throws IOException {
        List<Location> location = new ArrayList<>();
        location.add(new Location("777d17bc-a5fa-4a06-8146-b7c6e7040b8f", "gem", "GEM Center", "http://gemcenter.com.vn/Images/img/gem_logo.png"));
        Root root = new Root(0, "", new Data(location));

        Dispatcher dispatcher = new Dispatcher() {
            @NonNull
            @Override
            public MockResponse dispatch(@NonNull RecordedRequest recordedRequest) {
                if ("/v3/2fe37bd6-9dd0-4384-9a65-14ae709b82d9".equals(recordedRequest.getPath())) {
                    return new MockResponse().setResponseCode(200).setBody(root.toString());
                }
                return new MockResponse().setResponseCode(404);
            }
        };

        mockWebServer.setDispatcher(dispatcher);

        Call<Root> call = locationAPI.fetchAllLocation();
        Assert.assertFalse(call.isExecuted());
        MutableLiveData<Resource<Root>> liveData = locationRepository.getAllLocation();
        call.execute();
        Assert.assertTrue(call.isExecuted());

        liveData.observeForever(rootResource -> {

        });
        Assert.assertEquals(liveData.getValue().getStatus(), Status.SUCCESS);
        Assert.assertEquals(liveData.getValue().getData(), root);
    }

    @Test
    public void testFetchEmptyPreferLocation() throws IOException {

        Dispatcher dispatcher = new Dispatcher() {
            @NonNull
            @Override
            public MockResponse dispatch(@NonNull RecordedRequest recordedRequest) {
                if ("/v3/2fe37bd6-9dd0-4384-9a65-14ae709b82d9".equals(recordedRequest.getPath())) {
                    return new MockResponse().setResponseCode(404).setBody("{}");
                }
                return new MockResponse().setResponseCode(404);
            }
        };

        mockWebServer.setDispatcher(dispatcher);

        Call<Root> call = locationAPI.fetchAllLocation();
        Assert.assertFalse(call.isExecuted());
        MutableLiveData<Resource<Root>> liveData = locationRepository.getAllLocation();
        call.execute();
        Assert.assertTrue(call.isExecuted());

        liveData.observeForever(rootResource -> {

        });
        Assert.assertEquals(liveData.getValue().getStatus(), Status.ERROR);
        Assert.assertEquals(liveData.getValue().getData().getError_code(), 999);
        Assert.assertEquals(liveData.getValue().getData().getError_message(), "Empty data");
    }

    @Test
    public void testInternalServerErrorPreferLocation() throws IOException {

        Dispatcher dispatcher = new Dispatcher() {
            @NonNull
            @Override
            public MockResponse dispatch(@NonNull RecordedRequest recordedRequest) {
                if ("/v3/2fe37bd6-9dd0-4384-9a65-14ae709b82d9".equals(recordedRequest.getPath())) {
                    return new MockResponse().setResponseCode(200).setBody("{\n" +
                            "\"error_code\": 666,\n" +
                            "\"error_message\": \"Internal server error\"\n" +
                            "}");
                }
                return new MockResponse().setResponseCode(404);
            }
        };

        mockWebServer.setDispatcher(dispatcher);

        Call<Root> call = locationAPI.fetchAllLocation();
        Assert.assertFalse(call.isExecuted());
        MutableLiveData<Resource<Root>> liveData = locationRepository.getAllLocation();
        call.execute();
        Assert.assertTrue(call.isExecuted());

        liveData.observeForever(rootResource -> {

        });
        Assert.assertEquals(liveData.getValue().getStatus(), Status.ERROR);
        Assert.assertEquals(liveData.getValue().getData().getError_message(), "Internal server error");
        Assert.assertEquals(liveData.getValue().getData().getError_code(), 666);
    }

    // tests for fetchDetailLocation
    @Test
    public void testSuccessGetDetailLocation() throws IOException {
        RootDetail rootDetail = new RootDetail(0,
                "",
                new DataDetail(new LocationDetail("gem", "GEM Center", "GEM Center.", 10, 106)));

        Dispatcher dispatcher = new Dispatcher() {
            @NonNull
            @Override
            public MockResponse dispatch(@NonNull RecordedRequest recordedRequest) {
                if ("/v3/success".equals(recordedRequest.getPath())) {
                    return new MockResponse().setResponseCode(200).setBody("{\n" +
                            rootDetail +
                            "\n}");
                }
                return new MockResponse().setResponseCode(404);
            }
        };

        mockWebServer.setDispatcher(dispatcher);

        Call<RootDetail> call = locationAPI.fetchLocationDetail("success");
        Assert.assertFalse(call.isExecuted());
        MutableLiveData<Resource<RootDetail>> liveData = locationRepository.getLocationDetail("success");
        call.execute();
        Assert.assertTrue(call.isExecuted());

        liveData.observeForever(rootDetailResource -> {

        });

        Assert.assertEquals(liveData.getValue().getStatus(), Status.SUCCESS);
        Assert.assertEquals(liveData.getValue().getData(), rootDetail);
    }

    @Test
    public void testBadRequest() throws IOException {

        Dispatcher dispatcher = new Dispatcher() {
            @NonNull
            @Override
            public MockResponse dispatch(@NonNull RecordedRequest recordedRequest) {
                if ("/v3/success".equals(recordedRequest.getPath())) {
                    return new MockResponse().setResponseCode(404).setStatus("HTTP/1.1 404 Bad Request");
                }
                return new MockResponse().setResponseCode(404);
            }
        };

        mockWebServer.setDispatcher(dispatcher);

        Call<RootDetail> call = locationAPI.fetchLocationDetail("success");
        Assert.assertFalse(call.isExecuted());
        MutableLiveData<Resource<RootDetail>> liveData = locationRepository.getLocationDetail("success");
        call.execute();
        Assert.assertTrue(call.isExecuted());

        liveData.observeForever(rootDetailResource -> {

        });

        Assert.assertEquals(liveData.getValue().getStatus(), Status.ERROR);
        Assert.assertEquals(liveData.getValue().getData().getError_message(), "Bad Request");
    }

    @Test
    public void testLocationNotFound() throws IOException {

        Dispatcher dispatcher = new Dispatcher() {
            @NonNull
            @Override
            public MockResponse dispatch(@NonNull RecordedRequest recordedRequest) {
                if ("/v3/unknown".equals(recordedRequest.getPath())) {
                    return new MockResponse().setResponseCode(200).setBody("{\n" +
                            "\"error_code\": 600,\n" +
                            "\"error_message\": \"Unknown location.\"\n" +
                            "}");
                }
                return new MockResponse().setResponseCode(404);
            }
        };

        mockWebServer.setDispatcher(dispatcher);

        Call<RootDetail> call = locationAPI.fetchLocationDetail("unknown");
        Assert.assertFalse(call.isExecuted());
        MutableLiveData<Resource<RootDetail>> liveData = locationRepository.getLocationDetail("unknown");
        call.execute();
        Assert.assertTrue(call.isExecuted());

        liveData.observeForever(rootDetailResource -> {

        });

        Assert.assertEquals(liveData.getValue().getStatus(), Status.ERROR);
        Assert.assertEquals(liveData.getValue().getData().getError_message(), "Unknown location.");
        Assert.assertEquals(liveData.getValue().getData().getError_code(), 600);
    }

    @After
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }
}