package com.howkteam.foursquaresearch;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.howkteam.foursquaresearch.models.Venue;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
  private List<Venue> venues;

  public Adapter(List<Venue> venues) {
    this.venues = venues;
  }

  @Override
  public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item, parent, false);

    return new ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.tvName.setText(venues.get(position).name);
    holder.tvAddress.setText("Address: " + venues.get(position).location.address);
    holder.tvDistance.setText("Distance: " + String.valueOf(venues.get(position).location.distance));
  }

  @Override
  public int getItemCount() {
    return venues == null ? 0 : venues.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView tvName;
    public TextView tvAddress;
    public TextView tvDistance;

    public ViewHolder(View itemView) {
      super(itemView);
      tvName = (TextView) itemView.findViewById(R.id.tv_name);
      tvAddress = (TextView) itemView.findViewById(R.id.tv_address);
      tvDistance = (TextView) itemView.findViewById(R.id.tv_distance);
    }
  }
}
