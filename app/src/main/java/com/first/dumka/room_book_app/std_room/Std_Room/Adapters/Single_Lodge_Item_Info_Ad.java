package com.first.dumka.room_book_app.std_room.Std_Room.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.first.dumka.room_book_app.std_room.R;
import com.first.dumka.room_book_app.std_room.Std_Room.Modal_Class.Single_Lodge_Item_Info;

import java.util.ArrayList;

public class Single_Lodge_Item_Info_Ad extends RecyclerView.Adapter<Single_Lodge_Item_Info_Ad.ViewHolder> {


    ArrayList<Single_Lodge_Item_Info> list;
    Context context;
    private ItemClickListener mItemListener;


    public Single_Lodge_Item_Info_Ad(Context context, ArrayList<Single_Lodge_Item_Info> list, ItemClickListener itemClickListener) {
        this.list = list;
        this.context = context;
        this.mItemListener = itemClickListener;
    }



    @NonNull
    @Override
    public Single_Lodge_Item_Info_Ad.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lodge_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Single_Lodge_Item_Info_Ad.ViewHolder holder, int position) {
        Single_Lodge_Item_Info single_lodge_item_info = list.get(position);
        holder.lodge_name.setText(single_lodge_item_info.getLodge_name());
        holder.lodge_location.setText(single_lodge_item_info.getLodge_location());
        String bed_check = single_lodge_item_info.getBed();
        if(bed_check.equals("Bed Unavailable"))
        {
            holder.bed_available.setText(bed_check);
            holder.bed_available.setTextColor(Color.RED);
        }
        else {
            holder.bed_available.setText(bed_check);
            holder.bed_available.setTextColor(Color.BLUE);
        }

        Glide.with(context).load(single_lodge_item_info.getLodge_img()).into(holder.lodge_imgage);
        holder.itemView.setOnClickListener(view -> {
            mItemListener.onItemClick(list.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ItemClickListener {
        void onItemClick(Single_Lodge_Item_Info single_lodge_item_info);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView lodge_imgage;
        TextView lodge_name, lodge_location, bed_available;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lodge_imgage = itemView.findViewById(R.id.lodge_profile_image);
            lodge_name = itemView.findViewById(R.id.lodge_name);
            lodge_location = itemView.findViewById(R.id.lodge_location);
            bed_available = itemView.findViewById(R.id.bed_available);
        }
    }

}