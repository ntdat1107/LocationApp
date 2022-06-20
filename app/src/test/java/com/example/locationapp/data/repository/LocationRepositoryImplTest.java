package com.example.locationapp.data.repository;

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

}