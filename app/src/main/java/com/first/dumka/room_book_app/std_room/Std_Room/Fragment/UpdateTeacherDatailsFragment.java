package com.first.dumka.room_book_app.std_room.Std_Room.Fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.first.dumka.room_book_app.std_room.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class UpdateTeacherDatailsFragment extends Fragment {

    View view;
    ImageView thr_img;
    TextInputEditText thr_name, thr_qualification, thr_specialization, thr_emailid, thr_pass, thr_phoneno, thr_nearbtlocation, thr_fee;
    Spinner thr_gender, thr_status;
    String thre_name, thre_qualif, thre_special, thre_email, thre_pass, thre_phone, thre_location, thre_fee, thre_gender, thre_status;
    Button upd_btn;
    String check_gender, check_status;
    FloatingActionButton fbaddimg;
    Bitmap bitmap;
    Uri uri;
    String encodetImageString = "";
    String thr_avaialble_status,thr_image_path;
    String image_url = "http://stdroom.in/Android_khokan/Thr_Img/";
    private static String teacher_update_url = "http://stdroom.in/Android_khokan/teacher_update.php";

    public UpdateTeacherDatailsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_update_teacher_datails, container, false);
        thr_img = view.findViewById(R.id.teacher_profile);
        thr_name = view.findViewById(R.id.thr_name);
        thr_qualification = view.findViewById(R.id.thr_qualification);
        thr_specialization = view.findViewById(R.id.thr_specialization);
        thr_emailid = view.findViewById(R.id.thr_emailid);
        thr_phoneno = view.findViewById(R.id.thr_phone_no);
        thr_nearbtlocation = view.findViewById(R.id.thr_nearby_location);
        thr_fee = view.findViewById(R.id.thr_fee);
        thr_gender = view.findViewById(R.id.thr_gender);
        thr_status = view.findViewById(R.id.thr_status);
        thr_pass = view.findViewById(R.id.thr_pass);
        upd_btn = view.findViewById(R.id.upd_btn);
        fbaddimg = view.findViewById(R.id.fbaddimg);


        ArrayList<String> gender = new ArrayList<>();
        gender.add("Select Gender");
        gender.add("Male");
        gender.add("Female");

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, gender);
        thr_gender.setAdapter(arrayAdapter);

        ArrayList<String> status = new ArrayList<>();
        status.add("Select Status");
        status.add("Available");
        status.add("Unavailable");

        ArrayAdapter arrayAdapter1 = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, status);
        thr_status.setAdapter(arrayAdapter1);

        thr_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                check_status = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        thr_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                check_gender = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        Bundle data = getArguments();

        if (data != null) {

            String name = data.getString("thr_name");
            String thr_quli = data.getString("thr_quli");
            String thr_speci = data.getString("thr_speci");
            String thr_email = data.getString("thr_emailid");
            String thr_password = data.getString("thr_pass");
            String thr_phone = data.getString("thr_phone");
            String thr_location = data.getString("thr_location");
            String thr_fees = data.getString("thr_fee");
            String thr_imgs = data.getString("thr_img");
            String img_full_url = thr_imgs+image_url;

            thr_name.setText(name);
            thr_qualification.setText(thr_quli);
            thr_specialization.setText(thr_speci);
            thr_emailid.setText(thr_email);
            thr_pass.setText(thr_password);
            thr_phoneno.setText(thr_phone);
            thr_nearbtlocation.setText(thr_location);
            thr_fee.setText(thr_fees);
            thr_image_path = img_full_url+thr_imgs;

          Glide.with(getContext()).load(image_url+thr_imgs).into(thr_img);


            upd_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateDataDb();
                }
            });


        } else {
            Toast.makeText(getContext(), "Something went wrong!...", Toast.LENGTH_LONG).show();
        }


        fbaddimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(getContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Browse Photo"), 1);

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
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

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1 && data != null) {
            uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
//                thr_img.setImageBitmap(bitmap);
                thr_img.setImageURI(uri);
              encodedImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Toast.makeText(getContext(),"Image Not Selected! Please Try Again...", Toast.LENGTH_SHORT).show();
        }
    }
    private void encodedImageBitmap(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
         bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        encodetImageString = android.util.Base64.encodeToString(bytes, android.util.Base64.DEFAULT);
    }


    private void updateDataDb() {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Updating...");
        progressDialog.setCancelable(false);
        thre_name = thr_name.getText().toString().trim();
        thre_qualif = thr_qualification.getText().toString().trim();
        thre_special = thr_specialization.getText().toString().trim();
        thre_email = thr_emailid.getText().toString().trim();
        thre_phone = thr_phoneno.getText().toString().trim();
        thre_location = thr_nearbtlocation.getText().toString().trim();
        thre_fee = thr_fee.getText().toString().trim();
        thre_pass = thr_pass.getText().toString().trim();

        if (thre_name.equals("")) {
            thr_name.setError("Name is empty...");
        } else if (thre_qualif.equals("")) {
            thr_qualification.setError("Qualification is empty...");
        } else if (thre_special.equals("")) {
            thr_specialization.setError("Specialization is empty...");
        } else if (thre_email.equals("")) {
            thr_emailid.setError("Email id is empty...");
        } else if (thre_phone.equals("")) {
            thr_phoneno.setError("Phone no. is empty...");
        } else if (thre_location.equals("")) {
            thr_nearbtlocation.setError("Location is empty...");
        } else if (thre_fee.equals("")) {
            thr_fee.setError("Fee is empty...");
        } else if (check_gender.equals("Select Gender")) {
            Toast.makeText(getContext(), "Please, Select Your Gender", Toast.LENGTH_SHORT).show();
        } else if (check_status.equals("Select Status")) {
            Toast.makeText(getContext(), "Please, Select Your Status", Toast.LENGTH_SHORT).show();
        }else if (thre_pass.equals("")) {
            thr_pass.setError("Password id empty...");
        } else {

            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, teacher_update_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String result = response.trim();

                    if(result.equalsIgnoreCase("success"))
                    {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Update Successful...", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(view).navigate(R.id.action_updateTeacherDatailsFragment_to_teacherListFragment);
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Something went wrong!...", Toast.LENGTH_SHORT).show();

                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {

                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> prm = new HashMap<String, String>();
                    if(encodetImageString.equals("")) {
                        prm.put("teacher_name", thre_name);
                        prm.put("qualification", thre_qualif);
                        prm.put("specialization", thre_special);
                        prm.put("email_id", thre_email);
                        prm.put("phone_no", thre_phone);
                        prm.put("location_nearby", thre_location);
                        prm.put("fee", thre_fee);
                        prm.put("gender", check_gender);
                        prm.put("status", check_status);
                        prm.put("thr_pass",thre_pass);
                    }else {
                        prm.put("teacher_name", thre_name);
                        prm.put("qualification", thre_qualif);
                        prm.put("specialization", thre_special);
                        prm.put("email_id", thre_email);
                        prm.put("phone_no", thre_phone);
                        prm.put("location_nearby", thre_location);
                        prm.put("fee", thre_fee);
                        prm.put("gender", check_gender);
                        prm.put("status", check_status);
                        prm.put("thr_pass",thre_pass);
                        prm.put("img", encodetImageString);
                    }

                    return prm;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(getContext());
            queue.add(stringRequest);
        }

    }

}