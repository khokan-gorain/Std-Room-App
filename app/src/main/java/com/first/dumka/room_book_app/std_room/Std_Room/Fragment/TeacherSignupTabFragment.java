package com.first.dumka.room_book_app.std_room.Std_Room.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.provider.DocumentsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.first.dumka.room_book_app.std_room.R;
import com.first.dumka.room_book_app.std_room.Std_Room.Activityes.HomeTuitionPayment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class TeacherSignupTabFragment extends Fragment {

  View view;
  LinearLayout fragment_replace_lyt,otp_lyt;
  TextInputEditText thr_name,thr_phone_no;
  Button thr_getotp_btn,veryfy_opt_btn;
  LinearLayoutCompat thrlogindesign_lyt;
  String name,phone;
  private FirebaseAuth kAuth;
  EditText etC1,etC2,etC3,etC4,etC5,etC6;
  String verificationid;
  private final String number_check = "http://stdroom.in/Android_khokan/teacher_phone_number_verify.php";
  private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    public TeacherSignupTabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_teacher_signup_tab, container, false);
        kAuth = FirebaseAuth.getInstance();
        thr_name = view.findViewById(R.id.thr_name);
        thr_phone_no = view.findViewById(R.id.thr_phone_no);
        thr_getotp_btn = view.findViewById(R.id.get_otp_btn);
        fragment_replace_lyt = view.findViewById(R.id.lyt_raplace);
        thrlogindesign_lyt = view.findViewById(R.id.thrlogindesign_lyt);
        otp_lyt = view.findViewById(R.id.otp_lyt);
        veryfy_opt_btn = view.findViewById(R.id.verify_otp_btn);

        etC1 = view.findViewById(R.id.etC1);
        etC2 = view.findViewById(R.id.etC2);
        etC3 = view.findViewById(R.id.etC3);
        etC4 = view.findViewById(R.id.etC4);
        etC5 = view.findViewById(R.id.etC5);
        etC6 = view.findViewById(R.id.etC6);

        thr_getotp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = thr_name.getText().toString().trim();
               phone = thr_phone_no.getText().toString().trim();

                if(name.equals(""))
                {
                    thr_name.setError("Name is empty...");
                }
                else if(phone.equals(""))
                {
                    thr_phone_no.setError("Phone number is empty...");
                }
                else if(phone.length() != 10)
                {
                    Toast.makeText(getContext(), "Invalid phone number", Toast.LENGTH_LONG).show();
                }
                else
                {
                    checkNumberIsExist();
                }

            }
        });
        veryfy_opt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                veryfyOtp(verificationid);
            }
        });
        edTextInput();
        return view;
    }

    public void sendOtp(){

        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Sending otp...");
        dialog.setCancelable(false);
        dialog.show();
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
                thr_name.setEnabled(false);
                thr_phone_no.setEnabled(false);
                verificationid = verificationId;
                thr_getotp_btn.setVisibility(View.GONE);
                otp_lyt.setVisibility(View.VISIBLE);
                veryfy_opt_btn.setVisibility(View.VISIBLE);

            }
        };

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(kAuth)
                        .setPhoneNumber("+91"+phone)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(getActivity())
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

   public void veryfyOtp(String verificationId) {
       final ProgressDialog dialog = new ProgressDialog(getContext());
       dialog.setMessage("Verifying otp...");
       String c1 = etC1.getText().toString().trim();
       String c2 = etC2.getText().toString().trim();
       String c3 = etC3.getText().toString().trim();
       String c4 = etC4.getText().toString().trim();
       String c5 = etC5.getText().toString().trim();
       String c6 = etC6.getText().toString().trim();

       if (c1.isEmpty() || c2.isEmpty() || c3.isEmpty() || c4.isEmpty() || c5.isEmpty() || c6.isEmpty()) {
           Toast.makeText(getContext(), "Please fill opt...", Toast.LENGTH_SHORT).show();
       } else if (verificationId != null) {
           dialog.show();
           String code = c1 + c2 + c3 + c4 + c5 + c6;
           PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
           FirebaseAuth
                   .getInstance()
                   .signInWithCredential(credential)
                   .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                           if (task.isSuccessful()) {

                               dialog.dismiss();
                               Bundle data = new Bundle();
                               data.putString("name", name);
                               data.putString("phone", phone);
                               Navigation.findNavController(view).navigate(R.id.action_teacherLogingFragment_to_homeTuitionPayment,data);

                           } else {
                               dialog.dismiss();
                               Toast.makeText(getContext(), "Invalid OTP", Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
       } else {
           dialog.dismiss();
           Toast.makeText(getContext(), "Invalid OTP", Toast.LENGTH_SHORT).show();
       }
   }

   public void edTextInput() {
           etC1.addTextChangedListener(new TextWatcher() {
               @Override
               public void beforeTextChanged(CharSequence s, int start, int count, int after) {

               }

               @Override
               public void onTextChanged(CharSequence s, int start, int before, int count) {
                   etC2.requestFocus();
               }

               @Override
               public void afterTextChanged(Editable s) {

               }
           });
           etC2.addTextChangedListener(new TextWatcher() {
               @Override
               public void beforeTextChanged(CharSequence s, int start, int count, int after) {

               }

               @Override
               public void onTextChanged(CharSequence s, int start, int before, int count) {
                   etC3.requestFocus();
               }

               @Override
               public void afterTextChanged(Editable s) {

               }
           });
           etC3.addTextChangedListener(new TextWatcher() {
               @Override
               public void beforeTextChanged(CharSequence s, int start, int count, int after) {

               }

               @Override
               public void onTextChanged(CharSequence s, int start, int before, int count) {
                   etC4.requestFocus();
               }

               @Override
               public void afterTextChanged(Editable s) {

               }
           });
           etC4.addTextChangedListener(new TextWatcher() {
               @Override
               public void beforeTextChanged(CharSequence s, int start, int count, int after) {

               }

               @Override
               public void onTextChanged(CharSequence s, int start, int before, int count) {
                   etC5.requestFocus();
               }

               @Override
               public void afterTextChanged(Editable s) {

               }
           });
           etC5.addTextChangedListener(new TextWatcher() {
               @Override
               public void beforeTextChanged(CharSequence s, int start, int count, int after) {

               }

               @Override
               public void onTextChanged(CharSequence s, int start, int before, int count) {
                   etC6.requestFocus();
               }

               @Override
               public void afterTextChanged(Editable s) {

               }
           });
       }

       public void checkNumberIsExist()
       {
           ProgressDialog dialog = new ProgressDialog(getContext());
           dialog.setMessage("Checking Phone Number...");
           dialog.setCancelable(false);
           dialog.show();
           StringRequest request = new StringRequest(Request.Method.POST, number_check, new Response.Listener<String>() {
               @Override
               public void onResponse(String response) {
                   dialog.dismiss();
                   if(response.equalsIgnoreCase("success"))
                   {
                       Toast.makeText(getContext(), "Your phone number already register...", Toast.LENGTH_LONG).show();
                   }
                   else
                   {
                       dialog.dismiss();
                       sendOtp();
                   }

               }
           }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {
                   dialog.dismiss();
                   Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
               }
           }){
               @Nullable
               @Override
                   protected Map<String, String> getParams() throws AuthFailureError {
                       HashMap<String, String> prm = new HashMap<String, String>();
                       prm.put("phone_no",phone);
                       return prm;
               }
           };
           RequestQueue queue = Volley.newRequestQueue(getContext());
           queue.add(request);
       }
}