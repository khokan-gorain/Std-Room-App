package com.first.dumka.room_book_app.std_room.Std_Room.Activityes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.first.dumka.room_book_app.std_room.R;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Thread SplashThread = new Thread()
        {
            @Override
            public void run() {
                try {
                    sleep(1000);
                    Intent intent = new Intent(Splash.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        };
        SplashThread.start();
    }
}