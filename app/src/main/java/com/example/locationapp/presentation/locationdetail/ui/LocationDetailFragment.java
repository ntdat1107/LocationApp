package com.example.locationapp.presentation.locationdetail.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
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
import com.example.locationapp.data.sources.remote.model.LocationDetail;
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
    private String locationApiCode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        locationApiCode = getArguments().getString("code");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLocationDetailBinding.inflate(inflater, container, false);
        swipeRefreshLayout = binding.swipeRefreshLayout;

        setUpBackground();
        locationViewModel = new ViewModelProvider(this).get(LocationViewModel.class);
        Log.i("test", locationViewModel.locationRepository.toString());
        Log.i("test", locationViewModel.toString());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getData(view);

        swipeRefreshLayout.setOnRefreshListener(this);

    }

    private void setUpBackground() {
        binding.detail.setVisibility(View.INVISIBLE);
        binding.loading.setVisibility(View.VISIBLE);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());
        assert getArguments() != null;
        binding.tvToolbar.setText(getArguments().getString("title", "LocationApp"));
    }

    @SuppressLint("SetTextI18n")
    private void getData(View view) {
        locationViewModel.getLocationDetailMutableLiveData().observe(requireActivity(), rootDetail -> {
            if (rootDetail != null) {
                if (rootDetail.getError_code() == 0) {
                    // Location found
                    binding.detail.setVisibility(View.VISIBLE);
                    bindData(view, rootDetail.getData().getLocation());
                } else {
                    binding.detail.setVisibility(View.GONE);
                }
            }
        });

        locationViewModel.getLoading().observe(requireActivity(), aBoolean -> {
            if (aBoolean) {
                binding.loading.setVisibility((View.VISIBLE));
                if (!swipeRefreshLayout.isRefreshing()) {
                    binding.detail.setVisibility(View.INVISIBLE);
                }
            } else {
                swipeRefreshLayout.setRefreshing(false);
                binding.loading.setVisibility(View.INVISIBLE);
            }
        });

        locationViewModel.getError_message().observe(requireActivity(), s -> {
            if (s != null) {
                binding.errorMsg.setVisibility(View.VISIBLE);
                binding.errorMsg.setText(s + "\nSwipe to refresh!!!");
                binding.detail.setVisibility(View.GONE);
            } else {
                binding.errorMsg.setVisibility(View.GONE);
            }
        });

        locationViewModel.fetchLocationData(locationApiCode);
    }

    private void bindData(View view, LocationDetail location) {
        assert getArguments() != null;
        Picasso.with(view.getContext()).load(getArguments().getString("image")).placeholder(R.drawable.img).into(binding.imageViewDescription);
        binding.textViewDescription.setText(location.getDescription());
        binding.tvTitle.setText(location.getName());

        binding.showMapBtn.setOnClickListener(
            v -> {
                Bundle bundle = new Bundle();
                bundle.putDouble("Lat", location.getLat());
                bundle.putDouble("Lng", location.getLng());
                Navigation.findNavController(view).navigate(R.id.action_locationDetailFragment_to_mapsFragment, bundle);
            }
        );
    }

    @Override
    public void onRefresh() {
        locationViewModel.fetchLocationData(locationApiCode);
    }
}