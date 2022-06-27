package com.example.locationapp.presentation.locationlist.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.locationapp.data.sources.model.preferlocation.Data;
import com.example.locationapp.data.sources.model.preferlocation.Location;
import com.example.locationapp.databinding.FragmentLocationListBinding;
import com.example.locationapp.presentation.locationlist.viewmodel.LocationListViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LocationListFragment extends Fragment implements LocationListAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    LocationListViewModel locationListViewModel;
    private FragmentLocationListBinding binding;
    private LocationListAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private final MutableLiveData<Data> recyclerLiveData = new MutableLiveData<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLocationListBinding.inflate(inflater, container, false);

        swipeRefreshLayout = binding.swipeRefreshLayout;
        locationListViewModel = new ViewModelProvider(this).get(LocationListViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView();
        setUpFilter();

        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void setUpRecyclerView() {
        adapter = new LocationListAdapter(requireContext());
        adapter.setOnItemClickListener(this);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    private void setUpFilter() {
        binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    private void setUpObserver() {
        locationListViewModel.getLocationsLiveData().observe(getViewLifecycleOwner(), this::observeLocationLiveData);

        locationListViewModel.getLoading().observe(requireActivity(), this::observeLoading);

        locationListViewModel.getError_message().observe(requireActivity(), this::observeErrorMessage);
    }

    @Override
    public void onItemClick(Location location, View container) {
        LocationListFragmentDirections.ActionLocationListFragmentToLocationDetailFragment action = LocationListFragmentDirections.actionLocationListFragmentToLocationDetailFragment(location);
        Navigation.findNavController(container).navigate(action);
    }

    @Override
    public void onRefresh() {
        locationListViewModel.fetchDataAPI();
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpObserver();
        locationListViewModel.fetchDataAPI();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        locationListViewModel.getLocationsLiveData().removeObserver(this::observeLocationLiveData);
        locationListViewModel.getLoading().removeObserver(this::observeLoading);
        locationListViewModel.getError_message().removeObserver(this::observeErrorMessage);
    }

    private void observeLocationLiveData(List<Location> data) {
        if (data != null) {
            adapter.submitList(data);
            adapter.getFilter().filter(binding.searchBar.getQuery());
            binding.recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void observeLoading(Boolean aBoolean) {
        if (aBoolean) {
            binding.loading.setVisibility(View.VISIBLE);
        } else {
            swipeRefreshLayout.setRefreshing(false);
            binding.loading.setVisibility(View.INVISIBLE);
        }
    }

    private void observeErrorMessage(String s) {
        if (s != null) {
            binding.errorMsg.setVisibility(View.VISIBLE);
            binding.errorMsg.setText(s + "\nSwipe to refresh!!");
            binding.recyclerView.setVisibility(View.GONE);
        } else {
            binding.errorMsg.setVisibility(View.GONE);
        }
    }
}