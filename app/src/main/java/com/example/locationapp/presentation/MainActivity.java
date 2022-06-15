package com.example.locationapp.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.locationapp.R;
import com.example.locationapp.data.sources.remote.Data;
import com.example.locationapp.data.sources.remote.Location;
import com.example.locationapp.di.LocationComponent;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    LocationListViewModel locationListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fetchData();
    }

    private void fetchData() {
        locationListViewModel = new ViewModelProvider(this).get(LocationListViewModel.class);
        locationListViewModel.getLocationsLiveData().observe(this, new Observer<Data>() {
            @Override
            public void onChanged(Data data) {
                if (data == null) {
                    Toast.makeText(MainActivity.this, "NULL", Toast.LENGTH_SHORT).show();
                } else {
                    if (data.getLocations().size() == 0) {
                        Log.i("test", "no items");
                    } else {
                        for (Location i : data.getLocations()) {
                            Log.i("test", i.getName());
                        }
                    }
                }
            }
        });

        locationListViewModel.fetchDataAPI();
    }
}