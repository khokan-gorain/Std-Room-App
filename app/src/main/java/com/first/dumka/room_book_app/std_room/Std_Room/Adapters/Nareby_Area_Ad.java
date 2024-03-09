package com.first.dumka.room_book_app.std_room.Std_Room.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.first.dumka.room_book_app.std_room.R;
import com.first.dumka.room_book_app.std_room.Std_Room.Modal_Class.Nearby_Area;

import java.util.ArrayList;

public class Nareby_Area_Ad extends RecyclerView.Adapter<Nareby_Area_Ad.ViewHolder> {

    ArrayList<Nearby_Area> list;
    Context context;

    public Nareby_Area_Ad(ArrayList<Nearby_Area> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.whats_nearby_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Nearby_Area nearby_area = list.get(position);
        holder.area_name.setText(nearby_area.area_name);
        holder.area_distance.setText(nearby_area.area_distance);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView area_name,area_distance;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            area_name = itemView.findViewById(R.id.area_name);
            area_distance = itemView.findViewById(R.id.area_dis);
        }
    }
}
