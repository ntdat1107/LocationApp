package com.example.locationapp.presentation.locationlist.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationapp.R;
import com.example.locationapp.data.sources.model.preferlocation.Location;
import com.example.locationapp.databinding.LocationItemBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class LocationListAdapter extends ListAdapter<Location, LocationListAdapter.ViewHolder> implements Filterable {
    private List<Location> locations;
    private final Context context;
    public static final DiffUtil.ItemCallback<Location> diffUtilCallback = new LocationComparator();
    private final Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Location> filterLocations = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filterLocations.addAll(locations);
            } else {
                String input = constraint.toString().toLowerCase().trim();
                for (Location location: locations) {
                    if (location.getName().toLowerCase().contains(input)) {
                        filterLocations.add(location);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterLocations;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values instanceof List) {
                List<Location> filterLocations = new ArrayList<>((List) results.values);
                LocationListAdapter.super.submitList(filterLocations);
            }
        }
    };

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public LocationListAdapter(Context context) {
        super(diffUtilCallback);
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
        holder.bind(location);
    }

    @Override
    public Filter getFilter() {
        return this.filter;
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

    @Override
    public void submitList(@Nullable List<Location> list) {
        this.locations = list;
        super.submitList(list);
    }

    interface OnItemClickListener {
        void onItemClick(Location location, View container);
    }

    static class LocationComparator extends DiffUtil.ItemCallback<Location> {

        @Override
        public boolean areItemsTheSame(@NonNull Location oldItem, @NonNull Location newItem) {
            return oldItem.getCode().equals(newItem.getCode());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Location oldItem, @NonNull Location newItem) {
            return oldItem.equals(newItem);
        }
    }
}
