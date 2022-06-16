package com.example.locationapp.presentation.locationdetail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.locationapp.R;
import com.example.locationapp.data.sources.remote.model.LocationDetail;
import com.example.locationapp.data.sources.remote.model.Root;
import com.example.locationapp.data.sources.remote.model.RootDetail;
import com.example.locationapp.databinding.FragmentLocationDetailBinding;

public class LocationDetailFragment extends Fragment {
    LocationViewModel locationViewModel;
    private FragmentLocationDetailBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLocationDetailBinding.inflate(inflater, container, false);
        locationViewModel = new ViewModelProvider(requireActivity()).get(LocationViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv = view.findViewById(R.id.tv);
        assert getArguments() != null;
        tv.setText(getArguments().getString("code"));

        locationViewModel.getLocationDetailMutableLiveData().observe(requireActivity(), new Observer<RootDetail>() {
            @Override
            public void onChanged(RootDetail rootDetail) {
                if (rootDetail != null) {
                    if (rootDetail.getError_code() != 0) {
                        Log.i("test", rootDetail.getError_message());
                    } else {
                        Log.i("test", rootDetail.getData().getLocation().getDescription());
                    }
                }
            }
        });

        locationViewModel.fetchLocationData(getArguments().getString("code"));
    }
}