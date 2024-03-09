package com.first.dumka.room_book_app.std_room.Std_Room.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.first.dumka.room_book_app.std_room.R;
import com.first.dumka.room_book_app.std_room.Std_Room.Modal_Class.Lodge_Img_Slider_Class;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

public class Lodge_Img_Slider extends SliderViewAdapter<Lodge_Img_Slider.SliderViewHolder> {
    Context context;
    ArrayList<Lodge_Img_Slider_Class> list;
    public  Lodge_Img_Slider(Context context,ArrayList<Lodge_Img_Slider_Class> list)
    {
        this.context = context;
        this.list = list;
    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lodge_img_slider,null);
        return new Lodge_Img_Slider.SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {
    Lodge_Img_Slider_Class lodge_img_slider_class = list.get(position);
        Glide.with(context).load(lodge_img_slider_class.images).into(viewHolder.lodgeImg);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public static class SliderViewHolder extends SliderViewAdapter.ViewHolder{
        public ImageView lodgeImg;
        public SliderViewHolder(View itemView) {
            super(itemView);
            lodgeImg = itemView.findViewById(R.id.lodge_img_slider);
        }
    }
}
