package com.first.dumka.room_book_app.std_room.Std_Room.Fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.first.dumka.room_book_app.std_room.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class ContactusFragment extends Fragment {
    TextView mail_me;
    Button call_us_btn;
    static int PERMISSION_CODE= 100;


    public ContactusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contactus, container, false);

        mail_me = view.findViewById(R.id.mail_me);
        call_us_btn = view.findViewById(R.id.call_us);

//        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},PERMISSION_CODE);
//        }

        call_us_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withContext(getContext())
                      .withPermission(Manifest.permission.CALL_PHONE)
                      .withListener(new PermissionListener() {
                          @Override
                          public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                              String number = "7739542343";
                              Intent intent = new Intent(Intent.ACTION_CALL);
                              intent.setData(Uri.parse("tel:"+number));
                              startActivity(intent);
                          }

                          @Override
                          public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                             Intent intent = new Intent();
                             intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                             Uri uri = Uri.fromParts("package",getContext().getPackageName(),null);
                             intent.setData(uri);
                             startActivity(intent);

                          }

                          @Override
                          public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                            permissionToken.continuePermissionRequest();
                          }
                      }).check();

            }
        });

        mail_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Intent.ACTION_SENDTO);
                intent2.setData(Uri.parse("mailto:info.stdroom@gmail.com"));
                if(intent2.resolveActivity(getContext().getPackageManager()) != null){
                    startActivity(intent2);
                }
                else
                {
                    Toast.makeText(getContext(), "No app is installed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}