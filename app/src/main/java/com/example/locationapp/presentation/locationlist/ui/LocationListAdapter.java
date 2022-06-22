package com.example.locationapp.presentation.locationlist.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationapp.R;
import com.example.locationapp.data.sources.remote.model.preferlocation.Location;
import com.example.locationapp.databinding.LocationItemBinding;
import com.example.locationapp.presentation.locationlist.viewmodel.LocationListViewModel;
import com.squareup.picasso.Picasso;

public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.ViewHolder> {
    private final LocationListViewModel locationListViewModel;
    private final Context context;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

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
        private final LocationItemBinding binding;

        public ViewHolder(LocationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Location location) {
            Picasso.with(context).load(location.getImage()).placeholder(R.drawable.img).into(binding.imageViewAvatar);
            Picasso.with(context).load(location.getImage()).placeholder(R.drawable.img).into(binding.imageViewDescription);
            binding.textViewDescription.setText(location.getName());
            binding.textViewTitle.setText(location.getName());

            binding.cardView.setOnClickListener(v -> onItemClickListener.onItemClick(location, binding.cardView)
            );
        }
    }

    interface OnItemClickListener {
        void onItemClick(Location location, View container);
    }
}
