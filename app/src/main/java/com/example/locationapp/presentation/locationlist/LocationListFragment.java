package com.example.locationapp.presentation.locationlist;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.locationapp.databinding.FragmentLocationListBinding;

public class LocationListFragment extends Fragment {
    LocationListViewModel locationListViewModel;
    private FragmentLocationListBinding binding;
    private LocationListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLocationListBinding.inflate(inflater, container, false);

        locationListViewModel = new ViewModelProvider(requireActivity()).get(LocationListViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new LocationListAdapter(locationListViewModel, requireContext());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        fetchData();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void fetchData() {
        locationListViewModel.getLocationsLiveData().observe(requireActivity(), data -> {
            if (data != null) {
                adapter.notifyDataSetChanged();
            }
        });
    }
}