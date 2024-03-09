package com.first.dumka.room_book_app.std_room.Std_Room.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.first.dumka.room_book_app.std_room.R;
import com.first.dumka.room_book_app.std_room.Std_Room.Modal_Class.RuleAndRegulation;

import java.util.ArrayList;

public class Rule_And_Regulation_Ad extends RecyclerView.Adapter<Rule_And_Regulation_Ad.ViewHolder> {

  ArrayList<RuleAndRegulation> rule_list;
  Context context;

    public Rule_And_Regulation_Ad(ArrayList<RuleAndRegulation> rule_list, Context applicationContext) {
        this.rule_list = rule_list;
        this.context = applicationContext;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lyt_rule_and_reg,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RuleAndRegulation ruleAndRegulation = rule_list.get(position);
        holder.rule_name.setText(ruleAndRegulation.rule_text);
    }

    @Override
    public int getItemCount() {
        return rule_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView rule_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rule_name= itemView.findViewById(R.id.rule_name);
        }
    }
}
