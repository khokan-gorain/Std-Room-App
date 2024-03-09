package com.first.dumka.room_book_app.std_room.Std_Room.Activityes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.first.dumka.room_book_app.std_room.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class HomeTuitionPayment extends AppCompatActivity implements PaymentResultListener {

    ImageView thr_img;
    TextInputEditText thr_name, thr_qualification, thr_specialization, thr_emailid, thr_pass, thr_phoneno, thr_nearbtlocation, thr_fee;
    Spinner thr_gender, thr_status;
    String thre_name, thre_qualif, thre_special, thre_email, thre_pass, thre_phone, thre_location, thre_fee;
    Button pay_btn, signup_btn;
    String check_gender, check_status;
    FloatingActionButton fbaddimg;
    Bitmap bitmap ;
    Uri uri;
    int  total_amount = 0;
    String encodetImageString;
    String pay_amount = "0";
    private static String pay_amount_check = "http://stdroom.in/Android_khokan/thr_signup_pay_amount.php";
    private static String teachersignup_url = "http://stdroom.in/Android_khokan/teachersignup.php";

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_tuition_payment);
        getSupportActionBar().setTitle("Teacher Signup");
        thr_img = findViewById(R.id.teacher_profile);
        thr_name = findViewById(R.id.thr_name);
        thr_qualification = findViewById(R.id.thr_qualification);
        thr_specialization = findViewById(R.id.thr_specialization);
        thr_emailid = findViewById(R.id.thr_emailid);
        thr_phoneno = findViewById(R.id.thr_phone_no);
        thr_nearbtlocation = findViewById(R.id.thr_nearby_location);
        thr_fee = findViewById(R.id.thr_fee);
        thr_gender = findViewById(R.id.thr_gender);
        thr_status = findViewById(R.id.thr_status);
        thr_pass = findViewById(R.id.thr_pass);
        pay_btn = findViewById(R.id.pay_btn);
        signup_btn = findViewById(R.id.signup_btn);
        fbaddimg = findViewById(R.id.fbaddimg);
        Checkout.preload(getApplicationContext());

       teacherPayAmountCheck();

        ArrayList<String> gender = new ArrayList<>();
        gender.add("Select Gender");
        gender.add("Male");
        gender.add("Female");

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, gender);
        thr_gender.setAdapter(arrayAdapter);

        ArrayList<String> status = new ArrayList<>();
        status.add("Select Status");
        status.add("Available");
        status.add("Unavailable");

        ArrayAdapter arrayAdapter1 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, status);
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

        pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               uploadDataToDbWithPat();
            }
        });

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               uploadDataToDb();
            }
        });
        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.new_user);
        encodedImageBitmap(bitmap);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        encodetImageString = Base64.encodeToString(bytes, Base64.DEFAULT);

       Bundle data = getIntent().getExtras();

        if (data != null) {

            String name = data.getString("name");
            String thr_phone = data.getString("phone");

            thr_name.setText(name);
            thr_phoneno.setText(thr_phone);

        } else {
            Toast.makeText(getApplicationContext(), "Something went wrong...", Toast.LENGTH_LONG).show();
        }


        fbaddimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(getApplicationContext())
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
                                Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
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

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1 && data != null) {
            uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);
                thr_img.setImageBitmap(bitmap);
                encodedImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Something went wrong! Please try again...", Toast.LENGTH_SHORT).show();
        }

    }

    public void encodedImageBitmap(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        encodetImageString = Base64.encodeToString(bytes, Base64.DEFAULT);
    }


    public void uploadDataToDb() {

        ProgressDialog dialog = new ProgressDialog(HomeTuitionPayment.this);
        dialog.setMessage("Please Wait...");
        dialog.setCancelable(false);
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
            Toast.makeText(getApplicationContext(), "Please, Select Your Gender", Toast.LENGTH_SHORT).show();
        } else if (check_status.equals("Select Status")) {
            Toast.makeText(getApplicationContext(), "Please, Select Your Status", Toast.LENGTH_SHORT).show();
        }
        else if (thre_pass.equals("")) {
            thr_pass.setError("Password id empty...");
        } else {
            dialog.show();
            StringRequest stringRequest2 = new StringRequest(Request.Method.POST, teachersignup_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String result = response.trim();

                    if(result.equalsIgnoreCase("success"))
                    {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Signup Successful...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(HomeTuitionPayment.this,HomeTuitionActivity.class);
                        startActivity(intent);
                        finish();

                    }else
                    {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Something went wrong! Please try again...", Toast.LENGTH_LONG).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {

                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> prm = new HashMap<String, String>();
                    prm.put("teacher_name", thre_name);
                    prm.put("qualification", thre_qualif);
                    prm.put("specialization", thre_special);
                    prm.put("email_id", thre_email);
                    prm.put("phone_no", thre_phone);
                    prm.put("location_nearby", thre_location);
                    prm.put("fee", thre_fee);
                    prm.put("gender", check_gender);
                    prm.put("status", check_status);
                    prm.put("thr_pass", thre_pass);
                    prm.put("img", encodetImageString);

                    return prm;
                }
            };
            stringRequest2.setRetryPolicy(new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            RequestQueue queue = Volley.newRequestQueue(HomeTuitionPayment.this);
            queue.add(stringRequest2);
        }
    }

    public void uploadDataToDbWithPat() {

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
            Toast.makeText(getApplicationContext(), "Please, Select Your Gender", Toast.LENGTH_SHORT).show();
        } else if (check_status.equals("Select Status")) {
            Toast.makeText(getApplicationContext(), "Please, Select Your Status", Toast.LENGTH_SHORT).show();
        } else if (thre_pass.equals("")) {
            thr_pass.setError("Password id empty...");
        } else {

            startPayment();

        }
    }

    public void startPayment() {

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_live_HWabUvqVt2KTgE");

        checkout.setImage(R.drawable.logo);

        final Activity activity = HomeTuitionPayment.this;
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Std-Room");
            options.put("description", "Teacher Signup Fee");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //  options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#827717");
            options.put("currency", "INR");
            options.put("amount", total_amount);//pass amount in currency subunits
            options.put("prefill.email", thre_email);
            options.put("prefill.contact",thre_phone);
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
        Checkout.clearUserData(getApplicationContext());
    }

    @Override
    public void onPaymentSuccess(String s) {
        ProgressDialog dialog = new ProgressDialog(HomeTuitionPayment.this);
        dialog.setMessage("Please Wait...");
        dialog.setCancelable(false);
        dialog.show();
        String payment_id = s;

        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, teachersignup_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String result = response.trim();

                if(result.equalsIgnoreCase("success"))
                {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Signup Successful...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(HomeTuitionPayment.this,HomeTuitionActivity.class);
                    startActivity(intent);
                    finish();
                }else
                {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Something went wrong! Please try again...", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> prm = new HashMap<String, String>();
                prm.put("teacher_name", thre_name);
                prm.put("qualification", thre_qualif);
                prm.put("specialization", thre_special);
                prm.put("email_id", thre_email);
                prm.put("phone_no", thre_phone);
                prm.put("location_nearby", thre_location);
                prm.put("fee", thre_fee);
                prm.put("gender", check_gender);
                prm.put("status", check_status);
                prm.put("thr_pass", thre_pass);
                prm.put("pay_id",payment_id);
                prm.put("img", encodetImageString);

                return prm;
            }
        };
        stringRequest1.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest1);


    }

    @Override
    public void onPaymentError(int i, String s) {

        Toast.makeText(getApplicationContext(), "Payment Failed!...", Toast.LENGTH_LONG).show();
    }

    public void teacherPayAmountCheck() {
        StringRequest request = new StringRequest(Request.Method.POST, pay_amount_check, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("Amount");

                    if (success.equals("1")) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        pay_amount = jsonObject1.getString("signup_amount");
                        if(pay_amount.equals("0")){
                            signup_btn.setVisibility(View.VISIBLE);

                        }else{
                            String rupees = "\u20B9";
                            pay_btn.setText("Pay "+rupees+pay_amount+" only");
                            total_amount = Integer.parseInt(pay_amount) * 100;
                            pay_btn.setVisibility(View.VISIBLE);
                        }

                    }else{
                        Toast.makeText(getApplicationContext(), "Something went wrong!...", Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException exception){
                    Toast.makeText(getApplicationContext(), "Something went wrong!...", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

}