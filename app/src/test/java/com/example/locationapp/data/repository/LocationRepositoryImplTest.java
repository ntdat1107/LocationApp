package com.example.locationapp.data.repository;

<<<<<<< HEAD
import com.example.locationapp.data.sources.remote.LocationAPI;
import com.example.locationapp.domain.repository.LocationRepository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@RunWith(MockitoJUnitRunner.class)
public class LocationRepositoryImplTest {
    private String testDocumentPath = "/assets/success_response.json";
    private MockWebServer mockWebServer;
    private OkHttpClient client;

    private LocationAPI api;

    private LocationRepository locationRepositoryImpl;

//    @Before
//    public void setUp() {
//        mockWebServer = new MockWebServer();
//
//        client = new OkHttpClient.Builder().connectTimeout(1, TimeUnit.SECONDS)
//                .readTimeout(1, TimeUnit.SECONDS)
//                .writeTimeout(1, TimeUnit.SECONDS)
//                .build();
//
//        api = new Retrofit.Builder().baseUrl(mockWebServer.url("/"))
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(LocationAPI.class);
//
//        locationRepositoryImpl = new LocationRepositoryImpl(api);
//
//
//        try {
//            mockWebServer.start(8080);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @After
//    public void tearDown() {
//        try {
//            mockWebServer.shutdown();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void test() {
        String reader = new MockResponseFileReader().content;
        Assert.assertNotNull(reader);
    }

=======
import androidx.annotation.NonNull;
import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.locationapp.data.sources.local.LocalRepository;
import com.example.locationapp.data.sources.local.LocalRepositoryImpl;
import com.example.locationapp.data.sources.model.detaillocation.DataDetail;
import com.example.locationapp.data.sources.model.detaillocation.LocationDetail;
import com.example.locationapp.data.sources.model.detaillocation.RootDetail;
import com.example.locationapp.data.sources.remote.LocationAPI;
import com.example.locationapp.data.sources.remote.RemoteRepository;
import com.example.locationapp.data.sources.remote.RemoteRepositoryImpl;
import com.example.locationapp.utils.AppExecutors;
import com.example.locationapp.utils.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import mockwebserver3.Dispatcher;
import mockwebserver3.MockResponse;
import mockwebserver3.MockWebServer;
import mockwebserver3.RecordedRequest;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationRepositoryImplTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    private RemoteRepository remoteRepository;
    private final LocalRepository localRepository = Mockito.mock(LocalRepositoryImpl.class);

    LocationAPI locationAPI;

    MockWebServer mockWebServer;

    private LocationRepository locationRepository;

    @Before
    public void setUp() throws IOException {
        AppExecutors appExecutors = Mockito.mock(AppExecutors.class);
        Mockito.when(appExecutors.diskIO()).thenReturn(ArchTaskExecutor.getIOThreadExecutor());
        Mockito.when(appExecutors.mainThread()).thenReturn(ArchTaskExecutor.getIOThreadExecutor());

        mockWebServer = new MockWebServer();
        mockWebServer.start(8080);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(mockWebServer.url("").toString())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        locationAPI = retrofit.create(LocationAPI.class);

        remoteRepository = new RemoteRepositoryImpl(locationAPI);

        locationRepository = Mockito.spy(new LocationRepositoryImpl(remoteRepository, localRepository, appExecutors));
    }

    @Test
    public void testFetchFromLocal() throws IOException {
        LocationDetail locationDetail = new LocationDetail("success", "gem", "GEM Center", "GEM Center.", 10, 106);
        locationDetail.setTimestamp(System.currentTimeMillis() - 1111200);
        Mockito.when(localRepository.getDetailLocationFromDB("success")).thenReturn(new MutableLiveData<>(locationDetail));

        System.out.println(System.currentTimeMillis() - locationDetail.getTimestamp() > 5 * 60 * 1000);

        RootDetail rootDetail = new RootDetail(0,
                "",
                new DataDetail(new LocationDetail("success", "bku", "GEM Center", "GEM Center.", 10, 106)));

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

        call.execute();

        LiveData<Resource<LocationDetail>> result = locationRepository.getDetailLocation("success");

        result.observeForever(locationDetailResource -> {
        });

        Assert.assertEquals(result.getValue().getData(), locationDetail);
        System.out.println(result.getValue().getData());
    }
>>>>>>> Final
}