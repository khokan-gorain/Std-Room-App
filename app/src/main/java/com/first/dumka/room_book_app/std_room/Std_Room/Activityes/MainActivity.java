package com.first.dumka.room_book_app.std_room.Std_Room.Activityes;

import android.content.Intent;
import android.content.IntentSender;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.first.dumka.room_book_app.std_room.R;
import com.first.dumka.room_book_app.std_room.Std_Room.Fragment.AboutUsFragment;
import com.first.dumka.room_book_app.std_room.Std_Room.Fragment.ContactusFragment;
import com.first.dumka.room_book_app.std_room.Std_Room.Fragment.HomeFragment;
import com.first.dumka.room_book_app.std_room.Std_Room.Fragment.LoginFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.first.dumka.room_book_app.std_room.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;

public class MainActivity extends AppCompatActivity {

    public static int UPDATE_CODE = 22;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private NavigationView navigationView;
    private FirebaseAuth auth;
    public static Toolbar toolbar;
    AppUpdateManager appUpdateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        inAppUpdate(); // Update Notification
        toolbar = binding.appBarMain.toolbar;
        navigationView = findViewById(R.id.nav_view);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        FirebaseMessaging.getInstance().subscribeToTopic("Notification");
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,R.id.nav_login_page,R.id.nav_about_us_page)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(item -> {
            drawer.closeDrawers();
            switch (item.getItemId()){

                case R.id.nav_home:
                    HomeFragment homeFragment = new HomeFragment();
                    FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.add(R.id.nav_host_fragment_content_main,homeFragment).show(homeFragment);
                    fragmentTransaction1.addToBackStack(null);
                    fragmentTransaction1.commit();
                    break;

                case R.id.nav_login_page:
                    LoginFragment loginFragment = new LoginFragment();
                    FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction2.add(R.id.nav_host_fragment_content_main,loginFragment);
                    fragmentTransaction2.addToBackStack(null);
                    fragmentTransaction2.commit();
                    break;

                case R.id.nav_register_lodge:
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://stdroom.in/lodge_registration_form.php"));
                    startActivity(intent);
                    break;

                case R.id.nav_contact_us_page:
                    ContactusFragment contactusFragment = new ContactusFragment();
                    FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction3.replace(R.id.nav_host_fragment_content_main,contactusFragment);
                    fragmentTransaction3.addToBackStack(null);
                    fragmentTransaction3.commit();
                    break;

                case R.id.nav_about_us_page:
                    AboutUsFragment aboutUsFragment = new AboutUsFragment();
                    FragmentTransaction fragmentTransaction4 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction4.replace(R.id.nav_host_fragment_content_main,aboutUsFragment);
                    fragmentTransaction4.addToBackStack(null);
                    fragmentTransaction4.commit();
                    break;

                case R.id.nav_rate_us:
                    try {
                       // startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=com.first.dumka.room_book_app.std_room")));
                        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));

                    } catch (Exception ex) {
                       startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                        //startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=com.first.dumka.room_book_app.std_room")));
                        // website google play
                    }
                    break;

                case R.id.nav_share_app:
                    Intent myIntent = new Intent(Intent.ACTION_SEND);
                    myIntent.setType("text/plain");
                    String shareBody = "This is The best room booking App \n For every students of dumka. \n " +
                            "You will booked your favourite room \n just a single click.\n This is The Free App Hurry up ! \n Download  Now \n"
                            + "http://play.google.com/store/apps/details?id="+ getPackageName();
                    myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(myIntent, "Share using"));
                    break;

                case R.id.nav_privacy_policy:
                    Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://stdroom.in/privacy_policy.html"));
                    startActivity(intent1);
                    break;
            }

            return true;
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void inAppUpdate()
    {
        appUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());
        Task<AppUpdateInfo> task = appUpdateManager.getAppUpdateInfo();
        task.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
              if(appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE))
              {
                  try {
                      appUpdateManager.startUpdateFlowForResult(appUpdateInfo,AppUpdateType.IMMEDIATE,MainActivity.this,UPDATE_CODE);
                  } catch (IntentSender.SendIntentException e) {
                      Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                  }
              }
            }
        });
        appUpdateManager.registerListener(listener);
    }
    InstallStateUpdatedListener listener = installState ->
    {
        if(installState.installStatus() == InstallStatus.DOWNLOADED)
        {
            popUp();
        }

    };

    private void popUp() {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"App Update Successful",Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Reload", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUpdateManager.completeUpdate();
            }
        });
        snackbar.setTextColor(Color.parseColor("#827717"));
        snackbar.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == UPDATE_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                Toast.makeText(getApplicationContext(), "Ok", Toast.LENGTH_SHORT).show();
            }
        }
    }
}