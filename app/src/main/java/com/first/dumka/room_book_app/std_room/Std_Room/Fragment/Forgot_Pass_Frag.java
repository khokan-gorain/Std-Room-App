package com.first.dumka.room_book_app.std_room.Std_Room.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Forgot_Pass_Frag extends Fragment {
    EditText user_phone;
    Button send_otp_btn;
    TextView login_now;
    LinearLayout login_interface;
    String phone_verify = "http://stdroom.in/Android_khokan/phone_no_verify.php";
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;


    public Forgot_Pass_Frag() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_pass, container, false);
        mAuth = FirebaseAuth.getInstance();
        user_phone = view.findViewById(R.id.user_phone);
        send_otp_btn = view.findViewById(R.id.send_otp_btn);
        login_now = view.findViewById(R.id.login_now);
        login_interface = view.findViewById(R.id.login_interface);

        login_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginFragment loginFragment = new LoginFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.add(R.id.login_interface,loginFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        send_otp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendOpt();
            }
        });


        return view;
    }


    public void sendOpt()
    {
        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Sending OTP...");
        dialog.setCancelable(false);
        String phone_no = user_phone.getText().toString().trim();
        if(phone_no.isEmpty())
        {
            user_phone.setError("Phone number is empty...");
        }
        else if (phone_no.length() != 10)
        {
            Toast.makeText(getContext(), "Invalid Phone Number", Toast.LENGTH_SHORT).show();
        }
        else {
                    dialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, phone_verify, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.equalsIgnoreCase("success"))
                    {
                        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onVerificationCompleted(PhoneAuthCredential credential) {
                                dialog.dismiss();
                            }

                            @Override
                            public void onVerificationFailed(FirebaseException e) {
                                dialog.dismiss();
                                Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId,
                                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {

                                dialog.dismiss();
                                Bundle data = new Bundle();
                                data.putString("phone_no",phone_no);
                                data.putString("verification_code",verificationId);
                                Otp_Verification_frag otp_verification_frag = new Otp_Verification_frag();
                                otp_verification_frag.setArguments(data);
                                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.login_interface,otp_verification_frag);
                                fragmentTransaction.commit();

                            }
                        };

                        PhoneAuthOptions options =
                                PhoneAuthOptions.newBuilder(mAuth)
                                        .setPhoneNumber("+91" + phone_no)
                                        .setTimeout(60L, TimeUnit.SECONDS)
                                        .setActivity(getActivity())
                                        .setCallbacks(mCallbacks)
                                        .build();
                        PhoneAuthProvider.verifyPhoneNumber(options);
                    }

                    else
                    {
                        dialog.dismiss();
                        Toast.makeText(getContext(), "Phone number not register", Toast.LENGTH_SHORT).show();
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
                    Map<String,String> pram = new HashMap<>();
                    pram.put("phone_no",phone_no);
                    return pram;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);

        }
    }
}