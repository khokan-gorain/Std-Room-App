package com.first.dumka.room_book_app.std_room.Std_Room.Activityes;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.LinearLayout;

import android.widget.Spinner;

import com.first.dumka.room_book_app.std_room.R;
import com.first.dumka.room_book_app.std_room.Std_Room.Fragment.All_Boys_Lodge_Fragment;
import com.first.dumka.room_book_app.std_room.Std_Room.Fragment.Boys_Lodge_default_Item_Frag;

import java.util.ArrayList;

public class Boys_Lodge_Activity extends AppCompatActivity {


    String user_location_select;
    Spinner search_spinner;
    LinearLayout fragment_replace,all_lodge_frag;
    Boys_Lodge_default_Item_Frag boys_lodge_default_item_frag;
    All_Boys_Lodge_Fragment all_Boys_lodge_fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boys_lodge);
        getSupportActionBar().setTitle("Boys Lodge");
        search_spinner = findViewById(R.id.search_spinner);
        fragment_replace = findViewById(R.id.fragment_replace_layout);
        all_lodge_frag = findViewById(R.id.all_lodge_frag);



        ArrayList<String> location_name = new ArrayList<>();
        location_name.add("Bada Band");
        location_name.add("Railway Station");
        location_name.add("Lichi Bagan");
        location_name.add("Nagdih Rashikpur");
        location_name.add("Tower Chock");
        location_name.add("Gandhi Medan");

        ArrayAdapter arrayAdapter = new ArrayAdapter(Boys_Lodge_Activity.this, android.R.layout.simple_spinner_dropdown_item, location_name);
        search_spinner.setAdapter(arrayAdapter);

        search_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                user_location_select = adapterView.getItemAtPosition(i).toString();
                boys_lodge_default_item_frag = new Boys_Lodge_default_Item_Frag();
                all_Boys_lodge_fragment = new All_Boys_Lodge_Fragment();

                switch (user_location_select) {

                    case "Bada Band":
                    case "Railway Station":
                    case "Lichi Bagan":
                    case "Nagdih Rashikpur":
                    case "Tower Chock":
                    case "Gandhi Medan":
                        fragment_replace.setVisibility(View.VISIBLE);
                        all_lodge_frag.setVisibility(View.GONE);
                        Bundle data = new Bundle();
                        FragmentTransaction transaction;
                        data.putString("location", user_location_select);
                        boys_lodge_default_item_frag.setArguments(data);
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_replace_layout, boys_lodge_default_item_frag);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        getSupportActionBar().setTitle("");
        super.onDestroy();
    }
}
