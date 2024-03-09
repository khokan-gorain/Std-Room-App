package com.first.dumka.room_book_app.std_room.Std_Room.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.first.dumka.room_book_app.std_room.R;

import java.util.ArrayList;

public class TeacherListFragment extends Fragment {

    View view;
    Spinner search_spinner;
    LinearLayout fragment_replace_layout;
    String user_select_gender;

    public TeacherListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_teacher_list, container, false);

        fragment_replace_layout = view.findViewById(R.id.fragment_replace_layout);
        search_spinner = view.findViewById(R.id.search_spinner);


        ArrayList<String> gender = new ArrayList<>();
        gender.add("All Teacher");
        gender.add("Male");
        gender.add("Female");

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,gender);
        search_spinner.setAdapter(arrayAdapter);

        
       search_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               user_select_gender = adapterView.getItemAtPosition(i).toString();
               FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
               TeacherProfileListFragment teacherProfileListFragment = new TeacherProfileListFragment();
              // TeacherProfileFragment teacherProfileFragment = new TeacherProfileFragment();
               Bundle data = new Bundle();


               switch (user_select_gender){

                   case "All Teacher":
                   case "Male":
                   case "Female":
                       data.putString("user_select_gender", user_select_gender);
                       teacherProfileListFragment.setArguments(data);
                       transaction.replace(R.id.fragment_replace_layout,teacherProfileListFragment);
                       transaction.addToBackStack(null);
                       transaction.commit();
                       break;

               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });


        return view;
    }
}