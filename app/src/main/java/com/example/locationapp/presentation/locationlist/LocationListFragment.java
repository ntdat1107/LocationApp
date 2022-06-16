package com.example.locationapp.presentation.locationlist;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.locationapp.R;
import com.example.locationapp.data.sources.remote.Data;
import com.example.locationapp.data.sources.remote.Location;
import com.example.locationapp.databinding.FragmentLocationListBinding;
import com.example.locationapp.presentation.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class LocationListFragment extends Fragment {
    LocationListViewModel locationListViewModel;
    private FragmentLocationListBinding binding;
    private List<Location> locations = new ArrayList<>();
    private LocationListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLocationListBinding.inflate(inflater, container, false);

        locationListViewModel = new ViewModelProvider(requireActivity()).get(LocationListViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new LocationListAdapter(locations, requireContext());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        fetchData();
    }

    private void fetchData() {
        locationListViewModel.getLocationsLiveData().observe(requireActivity(), new Observer<Data>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(Data data) {
                if (data == null) {
                    Toast.makeText(requireActivity(), "NULL", Toast.LENGTH_SHORT).show();
                } else {
                    if (data.getLocations().size() == 0) {
                        Log.i("test", "no items");
                    } else {
                        locations.clear();
                        locations.addAll(data.getLocations());
                        Log.i("test", String.valueOf(locations.size()));
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}