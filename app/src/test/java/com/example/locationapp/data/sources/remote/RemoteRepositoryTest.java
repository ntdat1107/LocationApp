package com.example.locationapp.data.sources.remote;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.locationapp.data.sources.model.detaillocation.DataDetail;
import com.example.locationapp.data.sources.model.detaillocation.LocationDetail;
import com.example.locationapp.data.sources.model.detaillocation.RootDetail;
import com.example.locationapp.data.sources.model.preferlocation.Data;
import com.example.locationapp.data.sources.model.preferlocation.Location;
import com.example.locationapp.data.sources.model.preferlocation.Root;
import com.example.locationapp.data.sources.remote.LocationAPI;
import com.example.locationapp.data.sources.remote.RemoteRepository;
import com.example.locationapp.data.sources.remote.RemoteRepositoryImpl;
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
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    LocationAPI locationAPI;

    MockWebServer mockWebServer;

    RemoteRepository remoteRepository;

    @Before
    public void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start(8080);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(mockWebServer.url("").toString())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        locationAPI = retrofit.create(LocationAPI.class);

        remoteRepository = new RemoteRepositoryImpl(locationAPI);
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

        Call<Root> call = remoteRepository.getAllLocation();

        try {
            Response<Root> response = call.execute();
            Root responseRoot = response.body();
            Assert.assertEquals(responseRoot, root);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        Call<Root> call = remoteRepository.getAllLocation();

        try {
            Response<Root> response = call.execute();
            Root responseRoot = response.body();
            Assert.assertNull(responseRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        Call<Root> call = remoteRepository.getAllLocation();

        try {
            Response<Root> response = call.execute();
            Root responseRoot = response.body();
            Assert.assertEquals(responseRoot.getError_message(), "Internal server error");
            Assert.assertEquals(responseRoot.getError_code(), 666);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // tests for fetchDetailLocation
    @Test
    public void testSuccessGetDetailLocation() throws IOException {
        RootDetail rootDetail = new RootDetail(0,
                "",
                new DataDetail(new LocationDetail("success", "gem", "GEM Center", "GEM Center.", 10, 106)));

        Dispatcher dispatcher = new Dispatcher() {
            @NonNull
            @Override
            public MockResponse dispatch(@NonNull RecordedRequest recordedRequest) {
                if ("/v3/success".equals(recordedRequest.getPath())) {
                    return new MockResponse().setResponseCode(200).setBody(rootDetail.toString());
                }
                return new MockResponse().setResponseCode(404);
            }
        };

        mockWebServer.setDispatcher(dispatcher);

        Call<RootDetail> call = remoteRepository.getLocationDetail("success");

        try {
            Response<RootDetail> response = call.execute();
            RootDetail responseRootDetail = response.body();
            Assert.assertEquals(responseRootDetail, rootDetail);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        Call<RootDetail> call = remoteRepository.getLocationDetail("success");
        try {
            Response<RootDetail> response = call.execute();
            RootDetail responseRootDetail = response.body();
            Assert.assertNull(responseRootDetail);
            Assert.assertEquals(response.message(), "Bad Request");
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        Call<RootDetail> call = remoteRepository.getLocationDetail("unknown");

        try {
            Response<RootDetail> response = call.execute();
            RootDetail responseRootDetail = response.body();
            Assert.assertEquals(responseRootDetail.getError_message(), "Unknown location.");
            Assert.assertEquals(responseRootDetail.getError_code(), 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }
}