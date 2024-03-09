package com.first.dumka.room_book_app.std_room.Std_Room.Activityes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.first.dumka.room_book_app.std_room.R;
import com.first.dumka.room_book_app.std_room.Std_Room.Fragment.AboutUsFragment;
import com.first.dumka.room_book_app.std_room.Std_Room.Fragment.Lodge_Not_Found_Frag;
import com.first.dumka.room_book_app.std_room.Std_Room.Fragment.Login_Fragment2;
import com.first.dumka.room_book_app.std_room.Std_Room.Fragment.TeacherListFragment;
import com.first.dumka.room_book_app.std_room.Std_Room.Fragment.TeacherProfileListFragment;

public class HomeTuitionActivity extends AppCompatActivity {

    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_tuition);

        navController = Navigation.findNavController(HomeTuitionActivity.this,R.id.nav_fragment);
        NavigationUI.setupActionBarWithNavController(this,navController);

    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }
}


