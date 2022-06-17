package com.example.locationapp.presentation.locationlist;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.locationapp.R;
import com.example.locationapp.data.sources.remote.model.Location;
import com.example.locationapp.databinding.FragmentLocationListBinding;

public class LocationListFragment extends Fragment implements LocationListAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    LocationListViewModel locationListViewModel;
    private FragmentLocationListBinding binding;
    private LocationListAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

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

        locationListViewModel = new ViewModelProvider(requireActivity()).get(LocationListViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView();

        swipeRefreshLayout.setOnRefreshListener(this);
        fetchData();
    }

    private void setUpRecyclerView() {
        adapter = new LocationListAdapter(locationListViewModel, requireContext());
        adapter.setOnItemClickListener(this);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    @SuppressLint("NotifyDataSetChanged")
    private void fetchData() {
        locationListViewModel.getLocationsLiveData().observe(requireActivity(), data -> {
            if (data != null) {
                adapter.notifyDataSetChanged();
                binding.recyclerView.setVisibility(View.VISIBLE);
            }
        });

        locationListViewModel.getLoading().observe(requireActivity(), aBoolean -> {
            if (aBoolean) {
                Log.i("test", "loading");
                binding.loading.setVisibility(View.VISIBLE);
            } else {
                swipeRefreshLayout.setRefreshing(false);
                binding.loading.setVisibility(View.INVISIBLE);
            }
        });

        locationListViewModel.getError_msg().observe(requireActivity(), s -> {
            if (s != null) {
                Log.i ("test", s);
            }
        });
    }

    @Override
    public void onItemClick(Location location, View container) {
        Bundle bundle = new Bundle();
        bundle.putString("code", location.getId());
        bundle.putString("image", location.getImage());
        Navigation.findNavController(container).navigate(R.id.action_locationListFragment_to_locationDetailFragment, bundle);
    }

    @Override
    public void onRefresh() {
        locationListViewModel.fetchDataAPI();
    }
}