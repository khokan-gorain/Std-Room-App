package com.first.dumka.room_book_app.std_room.Std_Room.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.first.dumka.room_book_app.std_room.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Login_Fragment2 extends Fragment {

    EditText user_email,user_pass;
    TextView forgot_pass;
    Button login_btn;
    LinearLayout login_interface;
    View view;
    String login_url = "http://stdroom.in/Android_khokan/owner_login.php";


    public Login_Fragment2() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_login_2, container, false);

        user_email = view.findViewById(R.id.user_email);
        user_pass = view.findViewById(R.id.user_pass);
        forgot_pass = view.findViewById(R.id.forgot_pass);
        login_btn = view.findViewById(R.id.login_b);
        login_interface = view.findViewById(R.id.login_interface);




        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = user_email.getText().toString().trim();
                String pass = user_pass.getText().toString().trim();
              if(email.equals(""))
              {
                user_email.setError("Email id is empty...");
              }
              else if(pass.equals(""))
              {
                  user_pass.setError("Password is empty...");
              }
              else
              {
                  final ProgressDialog progressDialog = new ProgressDialog(getContext());
                  progressDialog.setMessage("Please Wait...");
                  progressDialog.setCancelable(false);
                  progressDialog.show();

                  StringRequest request = new StringRequest(Request.Method.POST, login_url, new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response) {
                          try {
                              progressDialog.dismiss();
                              JSONObject jsonObject = new JSONObject(response);
                              String success = jsonObject.getString("success");
                              JSONArray jsonArray = jsonObject.getJSONArray("BL_Room_Details");


                              if (jsonArray.length() != 0) {

                                  for (int i = 0; i < jsonArray.length(); i++) {
                                      JSONObject jsonObject2 = jsonArray.getJSONObject(0);

                                      String lodge_id = jsonObject2.getString("Lodge_Id");
                                      String lodge_name = jsonObject2.getString("Lodge_Name");
                                      String lodge_owner_name = jsonObject2.getString("Lodge_Owner_Name");

                                      String Single_bed_room = jsonObject2.getString("Single_Bed_Room");
                                      String Sig_total_room = jsonObject2.getString("Sig_Total_Room");
                                      String Sig_available_room  = jsonObject2.getString("Sig_Available_Room");
                                      String Sig_available_bed = jsonObject2.getString("Sig_Available_Bed");
                                      String Sig_roon_rent = jsonObject2.getString("Sig_Roon_Rent");


                                      String Double_bed_room = jsonObject2.getString("Double_Bed_Room");
                                      String Dou_total_room = jsonObject2.getString("Dou_Total_Room");
                                      String Dou_available_room = jsonObject2.getString("Dou_Available_Room");
                                      String Dou_available_bed= jsonObject2.getString("Dou_Available_Bed");
                                      String Dou_room_rent = jsonObject2.getString("Dou_Room_Rent");



                                      String Triple_bed_room = jsonObject2.getString("Triple_Bed_Room");
                                      String Tri_total_room = jsonObject2.getString("Tri_Total_Room");
                                      String Tri_available_room = jsonObject2.getString("Tri_Available_Room");
                                      String Tri_available_bed = jsonObject2.getString("Tri_Available_Bed");
                                      String Tri_room_rent = jsonObject2.getString("Tri_Room_Rent");


                                      String For_bed_room = jsonObject2.getString("For_Bed_Room");
                                      String For_total_room = jsonObject2.getString("For_Total_Room");
                                      String For_available_room = jsonObject2.getString("For_Available_Room");
                                      String For_available_bed = jsonObject2.getString("For_Available_Bed");
                                      String For_room_rent = jsonObject2.getString("For_Room_Rent");


                                      Bundle data = new Bundle();
                                      data.putString("Lodge_Id",lodge_id);
                                      data.putString("Lodge_Name",lodge_name);
                                      data.putString("Lodge_Owner_Name",lodge_owner_name);

                                      data.putString("Single_Bed_Room",Single_bed_room);
                                      data.putString("Sig_Total_Room",Sig_total_room);
                                      data.putString("Sig_Available_Room",Sig_available_room);
                                      data.putString("Sig_Available_Bed",Sig_available_bed);
                                      data.putString("Sig_Room_Rent",Sig_roon_rent);

                                      data.putString("Double_Bed_Room",Double_bed_room);
                                      data.putString("Dou_Total_Room",Dou_total_room);
                                      data.putString("Dou_Available_Room",Dou_available_room);
                                      data.putString("Dou_Available_Bed",Dou_available_bed);
                                      data.putString("Dou_Room_Rent",Dou_room_rent);

                                      data.putString("Triple_Bed_Room",Triple_bed_room);
                                      data.putString("Tri_Total_Room",Tri_total_room);
                                      data.putString("Tri_Available_Room",Tri_available_room);
                                      data.putString("Tri_Available_Bed",Tri_available_bed);
                                      data.putString("Tri_Room_Rent",Tri_room_rent);

                                      data.putString("For_Bed_Room",For_bed_room);
                                      data.putString("For_Total_Room",For_total_room);
                                      data.putString("For_Available_Room",For_available_room);
                                      data.putString("For_Available_Bed",For_available_bed);
                                      data.putString("For_Room_Rent",For_room_rent);

                                      Lodge_Update_Functionality lodge_update_functionality = new Lodge_Update_Functionality();
                                      lodge_update_functionality.setArguments(data);
                                      FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                                      transaction.replace(R.id.login_interface,lodge_update_functionality);
                                      transaction.addToBackStack(null);
                                      transaction.commit();


                                  }

                              } else {
                                  Toast.makeText(getContext(), "Invalid Email Id And Password", Toast.LENGTH_LONG).show();
                              }

                          } catch (JSONException e) {
                              Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
                              //Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                          }


                      }
                  }, new Response.ErrorListener() {
                      @Override
                      public void onErrorResponse(VolleyError error) {
                         progressDialog.dismiss();

                          Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                      }
                  }){
                      @Nullable
                      @Override
                      protected Map<String, String> getParams() throws AuthFailureError {
                          Map<String,String> prm = new HashMap<>();
                          prm.put("user_email",email);
                          prm.put("user_pass",pass);
                          return prm;
                      }
                  };
                  RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                  requestQueue.add(request);

              }

            }
        });

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Forgot_Pass_Frag forgot_pass_frag = new Forgot_Pass_Frag();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.login_interface,forgot_pass_frag);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
        return view;
    }
}