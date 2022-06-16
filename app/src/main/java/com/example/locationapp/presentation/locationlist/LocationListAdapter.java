package com.example.locationapp.presentation.locationlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationapp.data.sources.remote.model.Location;
import com.example.locationapp.databinding.LocationItemBinding;
import com.squareup.picasso.Picasso;

public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.ViewHolder> {
    private LocationListViewModel locationListViewModel;
    private Context context;

    public LocationListAdapter(LocationListViewModel locationListViewModel, Context context) {
        this.locationListViewModel = locationListViewModel;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LocationItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (this.locationListViewModel.getLocationsLiveData().getValue() != null) {
            Location location = this.locationListViewModel.getLocationsLiveData().getValue().getLocations().get(position);
            holder.bind(location);
        }
    }

    @Override
    public int getItemCount() {
        if (this.locationListViewModel.getLocationsLiveData().getValue() == null) {
            return 0;
        }
        return this.locationListViewModel.getLocationsLiveData().getValue().getLocations().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LocationItemBinding binding;

        public ViewHolder(LocationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Location location) {
            Picasso.with(context).load(location.getImage()).into(binding.imageViewAvatar);
            Picasso.with(context).load(location.getImage()).into(binding.imageViewDescription);
            binding.textViewDescription.setText(location.getName());
            binding.textViewTitle.setText(location.getName());
        }
    }
}
