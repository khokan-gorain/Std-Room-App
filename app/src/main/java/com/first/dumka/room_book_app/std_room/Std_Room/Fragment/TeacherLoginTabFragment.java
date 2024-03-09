package com.first.dumka.room_book_app.std_room.Std_Room.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavDestination;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.first.dumka.room_book_app.std_room.R;
import com.first.dumka.room_book_app.std_room.Std_Room.Modal_Class.Single_Lodge_Item_Info;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class TeacherLoginTabFragment extends Fragment {

 View view;
 Button thr_login_btn;
 TextInputEditText thr_nam,thr_pas;
 TextView forgot_pass;
 private static final String teacherlogin_url = "http://stdroom.in/Android_khokan/teacher_login.php";
    public TeacherLoginTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_teacher_login_tab, container, false);
        thr_login_btn = view.findViewById(R.id.thr_login_btn);
        thr_nam = view.findViewById(R.id.thr_namlog);
        thr_pas = view.findViewById(R.id.thr_password);
        forgot_pass = view.findViewById(R.id.forgot_pass);




        thr_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            loginTeacher();

               // Navigation.findNavController(view).navigate(R.id.action_teacherLogingFragment_to_updateTeacherDatailsFragment);

            }
        });

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_teacherLogingFragment_to_teacherForgotPasswordFragment);
            }
        });

        return view;
    }

   public void loginTeacher() {
        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Please Wait...");
        dialog.setCancelable(false);
        String th_name = thr_nam.getText().toString().trim();
        String th_pass = thr_pas.getText().toString().trim();

        if (th_name.equals("")) {
            thr_nam.setError("Name is empty...");
        } else if (th_pass.equals("")) {
            thr_pas.setError("Password is empty...");
        } else {
          dialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, teacherlogin_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        JSONArray jsonArray = jsonObject.getJSONArray("Teacher_Login_Details");
                        if (jsonArray.length() != 0) {

                            Bundle data = new Bundle();
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String thr_name = jsonObject1.getString("teacher_name");
                                String thr_quli = jsonObject1.getString("qualification");
                                String thr_speci = jsonObject1.getString("specialization");
                                String thr_emailid = jsonObject1.getString("email_id");
                                String thr_pass = jsonObject1.getString("password");
                                String thr_phone = jsonObject1.getString("phone_no");
                                String thr_location = jsonObject1.getString("location_nearby");
                                String thr_fee = jsonObject1.getString("fee");
                                String thr_gender = jsonObject1.getString("gender");
                                String thr_status = jsonObject1.getString("status");
                                String thr_img = jsonObject1.getString("img");

                                data.putString("thr_name",thr_name);
                                data.putString("thr_quli",thr_quli);
                                data.putString("thr_speci",thr_speci);
                                data.putString("thr_emailid",thr_emailid);
                                data.putString("thr_pass",thr_pass);
                                data.putString("thr_phone",thr_phone);
                                data.putString("thr_location",thr_location);
                                data.putString("thr_fee",thr_fee);
                                data.putString("thr_gender",thr_gender);
                                data.putString("thr_status",thr_status);
                                data.putString("thr_img",thr_img);

                            }

                            dialog.dismiss();
                            Navigation.findNavController(view).navigate(R.id.action_teacherLogingFragment_to_updateTeacherDatailsFragment,data);
                        } else {
                           dialog.dismiss();
                            Toast.makeText(getContext(), "Invalid name and password", Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        dialog.dismiss();
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();

                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> prm = new HashMap<>();
                    prm.put("thr_name", th_name);
                    prm.put("thr_pass", th_pass);
                    return prm;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);


        }
    }
}