package com.example.locationapp.presentation.mapdirection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.locationapp.R;
import com.example.locationapp.databinding.FragmentMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends Fragment implements OnMapReadyCallback {
    private FragmentMapsBinding binding;
    private GoogleMap mMap;
    private MarkerOptions curr, des;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMapsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        curr = new MarkerOptions().position(new LatLng(10.777580, 106.695978)).title("Current position");
        des = new MarkerOptions().position(new LatLng(10.772651, 106.658695)).title("Destination");

        assert getArguments() != null;
        double lat = getArguments().getDouble("Lat");
        double lng = getArguments().getDouble("Lng");
        des = new MarkerOptions().position(new LatLng(lat, lng)).title("Destination");

        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
//        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(@NonNull LatLng latLng) {
//                MarkerOptions markerOptions = new MarkerOptions();
//
//                markerOptions.position(latLng);
//                markerOptions.title(latLng.latitude + " : " +latLng.longitude);
//
//                googleMap.clear();
//
//                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
//
//                googleMap.addMarker(markerOptions);
//            }
//        });
        mMap.addMarker(des);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(des.getPosition(), 5));
    }


    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        double lat = 0, lng = 0;
        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);

            int dLat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dLat;
            shift = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);

            int dLng = ((result & 1) != 0) ? ~(result >> 1) : (result >> 1);
            lng += dLng;

            LatLng p = new LatLng((double) lat / 1E5, (double) lng / 1E5);
            poly.add(p);
        }
        return poly;
    }
}