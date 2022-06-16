package com.example.locationapp.presentation.locationlist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationapp.data.sources.remote.Location;
import com.example.locationapp.databinding.LocationItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.ViewHolder> {
    private List<Location> locations;
    private Context context;

    public LocationListAdapter(List<Location> locations, Context context) {
        this.locations = locations;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LocationItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Location location = locations.get(position);
        Log.i("test", location.getImage());

        Picasso.with(context).load(location.getImage()).into(holder.getBinding().imageViewAvatar);
        Picasso.with(context).load(location.getImage()).into(holder.getBinding().imageViewDescription);
        holder.getBinding().textViewDescription.setText(location.getName());
        holder.getBinding().textViewTitle.setText(location.getName());
    }

    @Override
    public int getItemCount() {
        return this.locations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LocationItemBinding binding;

        public ViewHolder(LocationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public LocationItemBinding getBinding() {
            return binding;
        }

        public void setBinding(LocationItemBinding binding) {
            this.binding = binding;
        }
    }
}
