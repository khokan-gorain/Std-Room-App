package com.first.dumka.room_book_app.std_room.Std_Room.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.first.dumka.room_book_app.std_room.R;
import com.first.dumka.room_book_app.std_room.Std_Room.Modal_Class.Single_Lodge_Item_Info;
import com.first.dumka.room_book_app.std_room.Std_Room.Modal_Class.TeacherList;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TeacherListAdapter extends RecyclerView.Adapter<TeacherListAdapter.ViewHolder> {

    ArrayList<TeacherList> list;
    Context context;
    private ItemClickListener itemClickListener;

    public TeacherListAdapter(ArrayList<TeacherList> list, Context context, ItemClickListener itemClickListener) {
        this.list = list;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lyt_teacher_list, parent, false);
         return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TeacherList teacherList = list.get(position);
        holder.tech_name.setText(teacherList.getTech_name());
        holder.tech_qualification.setText(teacherList.getTech_qualification());
        holder.tech_specialization.setText(teacherList.getTech_specialization());
        Glide.with(context).load("http://stdroom.in/Android_khokan/Thr_Img/"+teacherList.getTech_img()).into(holder.tech_img);

            String status = teacherList.getTech_status();
            if(status.equals("Available"))
            {
                holder.tech_status.setText("Available");
                holder.tech_status.setTextColor(Color.BLUE);
            }
            else
            {
                holder.tech_status.setText("Unavailable");
                holder.tech_status.setTextColor(Color.RED);
            }
        holder.itemView.setOnClickListener(view -> {
            itemClickListener.onItemClick(list.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ItemClickListener {
        void onItemClick(TeacherList teacherList);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView tech_img;
        TextView tech_name,tech_qualification,tech_specialization,tech_status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tech_img = itemView.findViewById(R.id.thr_img);
            tech_name = itemView.findViewById(R.id.thr_name);
            tech_qualification = itemView.findViewById(R.id.thr_qualification);
            tech_specialization = itemView.findViewById(R.id.thr_special);
            tech_status = itemView.findViewById(R.id.thr_status);
        }
    }
}
