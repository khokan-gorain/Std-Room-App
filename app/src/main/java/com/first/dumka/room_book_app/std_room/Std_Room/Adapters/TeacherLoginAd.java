package com.first.dumka.room_book_app.std_room.Std_Room.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.first.dumka.room_book_app.std_room.Std_Room.Fragment.TeacherLoginTabFragment;
import com.first.dumka.room_book_app.std_room.Std_Room.Fragment.TeacherSignupTabFragment;

public class TeacherLoginAd extends FragmentPagerAdapter {


    public TeacherLoginAd(@NonNull FragmentManager fm) {

        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0)
        {
            return new TeacherLoginTabFragment();
        }
        else
        {
            return new TeacherSignupTabFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        if(position == 0) {
            return "Login";
        }
        else {
            return "SignUp";
        }
    }
}
