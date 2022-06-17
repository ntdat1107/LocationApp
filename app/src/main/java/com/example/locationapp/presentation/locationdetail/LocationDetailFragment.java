package com.example.locationapp.presentation.locationdetail;

import android.content.Intent;
import android.net.Uri;
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

import com.example.locationapp.R;
import com.example.locationapp.data.sources.remote.model.RootDetail;
import com.example.locationapp.databinding.FragmentLocationDetailBinding;
import com.squareup.picasso.Picasso;

public class LocationDetailFragment extends Fragment {
    LocationViewModel locationViewModel;
    private FragmentLocationDetailBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLocationDetailBinding.inflate(inflater, container, false);
        setUpBackground();

        locationViewModel = new ViewModelProvider(requireActivity()).get(LocationViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData(view);

    }

    private void setUpBackground() {
        binding.detail.setVisibility(View.INVISIBLE);
        binding.loading.setVisibility(View.VISIBLE);
    }

    private void getData(View view) {
        assert getArguments() != null;
        locationViewModel.getLocationDetailMutableLiveData().observe(requireActivity(), rootDetail -> {
            if (rootDetail != null) {
                if (rootDetail.getError_code() == 0) {
                    // Location found
                    assert getArguments() != null;
                    binding.detail.setVisibility(View.VISIBLE);
                    Picasso.with(view.getContext()).load(getArguments().getString("image")).placeholder(R.drawable.img).into(binding.imageViewDescription);
                    binding.textViewDescription.setText(rootDetail.getData().getLocation().getDescription());
                    binding.tvTitle.setText(rootDetail.getData().getLocation().getName());
                } else {
                    binding.detail.setVisibility(View.GONE);
                }
            }
        });

        locationViewModel.getLoading().observe(requireActivity(), aBoolean -> {
            if (aBoolean) {
                binding.loading.setVisibility((View.VISIBLE));
                binding.detail.setVisibility(View.INVISIBLE);
            } else {
                binding.loading.setVisibility(View.INVISIBLE);
            }
        });

        locationViewModel.getError_message().observe(requireActivity(), s -> {
            if (s != null) {
                Log.i("test", s);
            } else {
                // Hide error msg
            }
        });

        locationViewModel.fetchLocationData(getArguments().getString("code"));
    }
}