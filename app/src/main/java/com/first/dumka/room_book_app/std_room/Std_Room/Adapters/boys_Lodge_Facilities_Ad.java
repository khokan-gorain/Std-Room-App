package com.first.dumka.room_book_app.std_room.Std_Room.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.first.dumka.room_book_app.std_room.R;
import com.first.dumka.room_book_app.std_room.Std_Room.Modal_Class.boys_lodge_facilities;

import java.util.ArrayList;

public class boys_Lodge_Facilities_Ad extends RecyclerView.Adapter<boys_Lodge_Facilities_Ad.ViewHolder> {

    ArrayList<boys_lodge_facilities> list;
    Context context;

    public boys_Lodge_Facilities_Ad(Context context,ArrayList<boys_lodge_facilities> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.facilities_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        boys_lodge_facilities boys_lodge_facilities = list.get(position);
        holder.facilities_name.setText(boys_lodge_facilities.facilities_name);
        Glide.with(context).load(boys_lodge_facilities.facilities_img).into(holder.facilities_img);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

         ImageView facilities_img;
         TextView facilities_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            facilities_img = itemView.findViewById(R.id.facilities_img);
            facilities_name = itemView.findViewById(R.id.facilities_name);
        }
    }
}
