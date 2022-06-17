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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        locationViewModel.getLocationDetailMutableLiveData().setValue(null);
    }

    private void setUpBackground() {
        binding.detail.setVisibility(View.INVISIBLE);
        binding.loading.setVisibility(View.VISIBLE);
    }

    private void getData(View view) {
        assert getArguments() != null;
        locationViewModel.getLocationDetailMutableLiveData().observe(requireActivity(), new Observer<RootDetail>() {
            @Override
            public void onChanged(RootDetail rootDetail) {
                if (rootDetail != null) {
                    if (rootDetail.getError_code() != 0) {
                        // Location not found
                        Log.i("test", rootDetail.getError_message());
                    } else {
                        Log.i("test", String.valueOf(rootDetail.getError_code()));
                        // Location found
                        assert getArguments() != null;
                        binding.loading.setVisibility(View.GONE);
                        binding.detail.setVisibility(View.VISIBLE);
                        Picasso.with(view.getContext()).load(getArguments().getString("image")).placeholder(R.drawable.img).into(binding.imageViewDescription);
                        binding.textViewDescription.setText(rootDetail.getData().getLocation().getDescription());
                        binding.tvTitle.setText(rootDetail.getData().getLocation().getName());

                    }
                }
            }
        });

        locationViewModel.fetchLocationData(getArguments().getString("code"));
    }
}