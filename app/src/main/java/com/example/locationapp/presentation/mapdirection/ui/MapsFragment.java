package com.example.locationapp.presentation.mapdirection.ui;

import android.content.ActivityNotFoundException;
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
import androidx.lifecycle.ViewModelProvider;

import com.example.locationapp.R;
import com.example.locationapp.data.sources.model.detaillocation.LocationDetail;
import com.example.locationapp.databinding.FragmentMapsBinding;
import com.example.locationapp.presentation.mapdirection.viewmodel.MapViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {
    private FragmentMapsBinding binding;
    private GoogleMap mMap;
    private MarkerOptions des;

    private MapViewModel mapViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMapsBinding.inflate(inflater, container, false);

        binding.driving.setOnClickListener(this);
        binding.bicycling.setOnClickListener(this);
        binding.walking.setOnClickListener(this);
        binding.streetView.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);
}

    private void observeLocationDetail(LocationDetail locationDetail) {
        if (locationDetail != null) {
            des = new MarkerOptions().position(new LatLng(locationDetail.getLat(), locationDetail.getLng())).title("Destination");
            mMap.addMarker(des);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(des.getPosition(), 16F));
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        assert getArguments() != null;
        LocationDetail locationDetail = MapsFragmentArgs.fromBundle(getArguments()).getLocationDetail();
        mapViewModel.getLocationDetailMutableLiveData().setValue(locationDetail);
        mapViewModel.getLocationDetailMutableLiveData().observe(getViewLifecycleOwner(), this::observeLocationDetail);
    }

    @Override
    public void onClick(View view) {
        if (binding.driving.equals(view)) {
            getDirectionByGgMap("d");
        } else if (binding.bicycling.equals(view)) {
            getDirectionByGgMap("b");
        } else if (binding.walking.equals(view)) {
            getDirectionByGgMap("w");
        } else if (binding.streetView.equals(view)) {
            getStreetViewByGgMap();
        }
    }

    private void getStreetViewByGgMap() {
        Uri uri = Uri.parse("google.streetview:cbll=" + des.getPosition().latitude + "," + des.getPosition().longitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.google.android.apps.maps");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        requireActivity().startActivity(intent);
    }

    private void getDirectionByGgMap(String mode) {
        try {
            // Navigation to google map app
            Uri uri = Uri.parse("google.navigation:q=" + des.getPosition().latitude + "," + des.getPosition().longitude + "&mode=" + mode);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            requireActivity().startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Google map is not installed
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            requireActivity().startActivity(intent);
        }

    }
}