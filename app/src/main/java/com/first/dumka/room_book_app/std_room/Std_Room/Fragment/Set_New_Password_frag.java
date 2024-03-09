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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.first.dumka.room_book_app.std_room.R;

import java.util.HashMap;
import java.util.Map;

public class Set_New_Password_frag extends Fragment {

    EditText new_pass,conf_pass;
    Button change_pass;
    String phone_no;
    String change_pass_url = "http://stdroom.in/Android_khokan/forgot_password.php";
    public Set_New_Password_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_set_new_password_frag, container, false);
        new_pass = view.findViewById(R.id.new_pass);
        conf_pass  = view.findViewById(R.id.conf_pass);
        change_pass = view.findViewById(R.id.change_pass_btn);

        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });

        Bundle data = getArguments();
        phone_no = data.getString("phone_no");

        return  view;
    }
    public void changePassword()
    {
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Changing Password...");
        dialog.setCancelable(false);
        String new_pass1 = new_pass.getText().toString().trim();
        String  conf_pass1 = conf_pass.getText().toString().trim();
        if (new_pass1.isEmpty())
        {
            new_pass.setError("New Password id empty...");
        }
        else if (conf_pass1.isEmpty())
        {
            conf_pass.setError("Confirm password is empty...");
        }
        else if(new_pass1.equals(conf_pass1))
        {
            dialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, change_pass_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.equals("success"))
                    {
                        dialog.dismiss();
                        Toast.makeText(getContext(), "Password Changed Successfully...", Toast.LENGTH_SHORT).show();
                        Login_Fragment2 login_fragment2 = new Login_Fragment2();
                        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                        transaction.replace(R.id.login_interface,login_fragment2);
                        transaction.commit();
                    }
                    else
                    {
                        dialog.dismiss();
                        Toast.makeText(getContext(), "Password Not Changed", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> prm = new HashMap<>();
                    prm.put("new_pass",new_pass1);
                    prm.put("phone_no",phone_no);

                    return prm;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);
        }
        else
        {
            Toast.makeText(getContext(), "Password Not Matching...", Toast.LENGTH_SHORT).show();
        }

    }
}