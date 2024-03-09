package com.first.dumka.room_book_app.std_room.Std_Room.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.first.dumka.room_book_app.std_room.R;
import com.first.dumka.room_book_app.std_room.Std_Room.Adapters.TeacherLoginAd;
import com.google.android.material.tabs.TabLayout;


public class TeacherLogingFragment extends Fragment {

    View view;
    ViewPager viewPager;
    TabLayout tabLayout;
    public TeacherLogingFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_teacher_loging, container, false);

        viewPager = view.findViewById(R.id.view_pager);
        tabLayout = view.findViewById(R.id.tab_lyt);


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        TeacherLoginAd teacherLoginAd = new TeacherLoginAd(getChildFragmentManager());
        viewPager.setAdapter(teacherLoginAd);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}