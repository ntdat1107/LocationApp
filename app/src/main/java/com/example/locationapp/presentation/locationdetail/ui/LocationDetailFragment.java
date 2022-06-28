package com.example.locationapp.presentation.locationdetail.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.locationapp.R;
import com.example.locationapp.data.sources.model.detaillocation.LocationDetail;
import com.example.locationapp.data.sources.model.detaillocation.RootDetail;
import com.example.locationapp.data.sources.model.preferlocation.Location;
import com.example.locationapp.databinding.FragmentLocationDetailBinding;
import com.example.locationapp.presentation.locationdetail.viewmodel.LocationViewModel;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LocationDetailFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    LocationViewModel locationViewModel;
    private FragmentLocationDetailBinding binding;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Location location;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        location = LocationDetailFragmentArgs.fromBundle(getArguments()).getLocation();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLocationDetailBinding.inflate(inflater, container, false);
        swipeRefreshLayout = binding.swipeRefreshLayout;

        setUpBackground();
        locationViewModel = new ViewModelProvider(this).get(LocationViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpObserve();

        this.view = view;

        swipeRefreshLayout.setOnRefreshListener(this);

    }

    private void setUpBackground() {
        binding.detail.setVisibility(View.INVISIBLE);
        binding.loading.setVisibility(View.VISIBLE);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());
        binding.tvToolbar.setText(location.getName());
    }

    @SuppressLint("SetTextI18n")
    private void setUpObserve() {
        locationViewModel.getLocationDetailMutableLiveData().observe(getViewLifecycleOwner(), this::observeLocationDetail);

        locationViewModel.getLoading().observe(getViewLifecycleOwner(), this::observeLoading);

        locationViewModel.getError_message().observe(getViewLifecycleOwner(), this::observeErrorMessage);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        locationViewModel.getLocationDetailMutableLiveData().removeObserver(this::observeLocationDetail);

        locationViewModel.getLoading().removeObserver(this::observeLoading);

        locationViewModel.getError_message().removeObserver(this::observeErrorMessage);
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpObserve();
        locationViewModel.fetchLocationData(location.getId());
    }

    private void bindData(LocationDetail locationDetail) {
        Picasso.with(view.getContext()).load(location.getImage()).placeholder(R.drawable.img).into(binding.imageViewDescription);
        binding.textViewDescription.setText(locationDetail.getDescription());
        binding.tvTitle.setText(locationDetail.getName());

        binding.showMapBtn.setOnClickListener(
                v -> {
                    LocationDetailFragmentDirections.ActionLocationDetailFragmentToMapsFragment action = LocationDetailFragmentDirections.actionLocationDetailFragmentToMapsFragment(locationDetail);
                    Navigation.findNavController(view).navigate(action);
                }
        );
    }

    @Override
    public void onRefresh() {
        locationViewModel.fetchLocationData(location.getId());
    }

    private void observeLoading(Boolean aBoolean) {
        if (aBoolean) {
            binding.loading.setVisibility((View.VISIBLE));
            if (!swipeRefreshLayout.isRefreshing()) {
                binding.detail.setVisibility(View.INVISIBLE);
            }
        } else {
            swipeRefreshLayout.setRefreshing(false);
            binding.loading.setVisibility(View.INVISIBLE);
        }
    }

    private void observeErrorMessage(String s) {
        if (s != null) {
            binding.errorMsg.setVisibility(View.VISIBLE);
            binding.errorMsg.setText(s + "\nSwipe to refresh!!!");
            binding.detail.setVisibility(View.GONE);
        } else {
            binding.errorMsg.setVisibility(View.GONE);
        }
    }

    private void observeLocationDetail(LocationDetail locationDetail) {
        if (locationDetail != null) {
            binding.detail.setVisibility(View.VISIBLE);
            bindData(locationDetail);
        } else {
            binding.detail.setVisibility(View.GONE);
        }
    }
}