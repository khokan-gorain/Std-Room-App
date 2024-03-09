package com.first.dumka.room_book_app.std_room.Std_Room.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.first.dumka.room_book_app.std_room.R;
import com.first.dumka.room_book_app.std_room.Std_Room.Modal_Class.Slider_Img_Class;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderViewHolder> {

   Context context;
   ArrayList<Slider_Img_Class> list;

    public SliderAdapter(Context context, ArrayList<Slider_Img_Class> list) {
       this.list = list;
        this.context = context;
    }



    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item_layout,null);
        return new SliderAdapter.SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {
        Slider_Img_Class slider_img_class = list.get(position);
        Glide.with(context).load("http://stdroom.in/Images/bl_img/"+slider_img_class.getImage()).into(viewHolder.imageView);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public static class SliderViewHolder extends SliderViewAdapter.ViewHolder{

        public final ImageView imageView;
        public SliderViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.slider_image_view);
        }
    }
}
