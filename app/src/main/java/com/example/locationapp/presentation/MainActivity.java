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
import com.example.locationapp.presentation.locationlist.LocationListViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


}