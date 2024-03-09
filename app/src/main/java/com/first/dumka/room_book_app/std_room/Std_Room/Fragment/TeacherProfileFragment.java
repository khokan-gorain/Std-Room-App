package com.first.dumka.room_book_app.std_room.Std_Room.Fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.first.dumka.room_book_app.std_room.R;
import com.first.dumka.room_book_app.std_room.Std_Room.Modal_Class.TeacherList;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class TeacherProfileFragment extends Fragment {

    View view;
    ImageView thr_img;
    TextView thr_name,thr_qualification,thr_specialization,thr_location,thr_fee;
    Button thr_call_btn;
    private String teacher_id;
    private String teacherprofiledetails_url = "http://stdroom.in/Android_khokan/teacherprofiledetalis.php";
    private String teacher_img_link = "http://stdroom.in/Images/teacher_img/";
    public static String  std_room_call="7739542343";
    ShimmerFrameLayout shimmer_teacherprofile;
    LinearLayout teacherprofiledesign;

    public TeacherProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view =  inflater.inflate(R.layout.fragment_teacher_profile, container, false);
         thr_img = view.findViewById(R.id.thr_profile_img);
         thr_name = view.findViewById(R.id.thr_name);
         thr_qualification = view.findViewById(R.id.thr_qualification);
         thr_specialization = view.findViewById(R.id.thr_special);
         thr_location = view.findViewById(R.id.thr_location);
         thr_fee = view.findViewById(R.id.thr_fee);
         thr_call_btn= view.findViewById(R.id.call_btn);
         shimmer_teacherprofile = view.findViewById(R.id.shimmer_teacherprofile);
         teacherprofiledesign = view.findViewById(R.id.teacherprofiledesign);

           Bundle data = getArguments();
           if(data != null) {
               teacher_id = data.getString("teacher_id");
               // Toast.makeText(getContext(), teacher_id, Toast.LENGTH_SHORT).show();
               teacherProfileDetails();
           }
           else
           {
               Toast.makeText(getContext(), "Something went worong! Try Again...", Toast.LENGTH_SHORT).show();
           }

        return view;
    }

    public void teacherProfileDetails()
    {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, teacherprofiledetails_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("Teacher_Profile_Details");


                    if (success.equals("1")) {
                        shimmer_teacherprofile.stopShimmer();
                        shimmer_teacherprofile.hideShimmer();
                        shimmer_teacherprofile.setVisibility(View.GONE);
                        teacherprofiledesign.setVisibility(View.VISIBLE);


                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String teacher_name = jsonObject1.getString("teacher_name");
                            String teacher_pho_no = jsonObject1.getString("phone_no");
                            String teacher_qualification = jsonObject1.getString("qualification");
                            String teacher_specialization = jsonObject1.getString("specialization");
                            String teacher_location = jsonObject1.getString("location_nearby");
                            String  teacher_fee = jsonObject1.getString("fee");
                            String  teacher_gender = jsonObject1.getString("gender");
                            String teacher_img = jsonObject1.getString("img");
                            String teacher_status = jsonObject1.getString("status").trim();
                            String  teacher_call_status = jsonObject1.getString("call_status");
                           // std_room_call = jsonObject1.getString("std_room_call");

                            Glide.with(getContext()).load("http://stdroom.in/Android_khokan/Thr_Img/"+teacher_img).into(thr_img);
                            thr_name.setText(teacher_name);
                            thr_qualification.setText(teacher_qualification);
                            thr_specialization.setText(teacher_specialization);
                            thr_location.setText(teacher_location);
                            thr_fee.setText(teacher_fee);

                            thr_call_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(teacher_status.equals("Unavailable"))
                                    {
                                        Toast.makeText(getContext(), "Sorry, now "+teacher_name+" is unavailable", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Dexter.withContext(getContext())
                                                .withPermission(Manifest.permission.CALL_PHONE)
                                                .withListener(new PermissionListener() {
                                                    @Override
                                                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                                                        if(teacher_gender.equals("Female"))
                                                        {

                                                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                                                            alertDialog.setCancelable(false);
                                                            alertDialog.setIcon(R.drawable.ic_notification_24);
                                                            alertDialog.setTitle("Some Teacher Privacy");
                                                            alertDialog.setMessage("Due to some privacy policy your call will connect to Std-Room customer service center.");
                                                            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                                    Intent intent = new Intent(Intent.ACTION_CALL);
                                                                    intent.setData(Uri.parse("tel:"+std_room_call));
                                                                    startActivity(intent);
                                                                }
                                                            });
                                                            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                                   dialogInterface.dismiss();
                                                                }
                                                            });
                                                            AlertDialog dialog = alertDialog.create();
                                                            dialog.show();
                                                        }
                                                        else
                                                        {
                                                            Intent intent = new Intent(Intent.ACTION_CALL);
                                                            intent.setData(Uri.parse("tel:"+teacher_pho_no));
                                                            startActivity(intent);
                                                        }

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
                                }
                            });


                        }
                    } else {
                        Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("teacher_id",teacher_id);
                return hashMap;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}


