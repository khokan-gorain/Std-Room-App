package com.first.dumka.room_book_app.std_room.Std_Room.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.first.dumka.room_book_app.std_room.R;

public class Lodge_Not_Found_Frag extends Fragment {

    TextView notfoudntv;
    View view;
    public Lodge_Not_Found_Frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_lodge_not_found, container, false);
        notfoudntv = view.findViewById(R.id.notfoundtv);

        Bundle data = getArguments();
        if(data != null)
        {
            String msg = data.getString("msg");
            notfoudntv.setText(msg);
        }
        else
        {
            Toast.makeText(getContext(), "something went wrong...", Toast.LENGTH_SHORT).show();
        }


        return view;
    }
}