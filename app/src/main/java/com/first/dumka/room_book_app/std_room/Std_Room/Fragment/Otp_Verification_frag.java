package com.first.dumka.room_book_app.std_room.Std_Room.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.first.dumka.room_book_app.std_room.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;


public class Otp_Verification_frag extends Fragment {
    TextView phoneNo,resend_otp_btn;
    EditText etc1,etc2,etc3,etc4,etc5,etc6;
    Button verify_btn;
    String verification_id;
    String user_phone_no;

    public Otp_Verification_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_otp_verification_frag, container, false);
        phoneNo = view.findViewById(R.id.pho_no);
        etc1 = view.findViewById(R.id.etC1);
        etc2 = view.findViewById(R.id.etC2);
        etc3 = view.findViewById(R.id.etC3);
        etc4 = view.findViewById(R.id.etC4);
        etc5 = view.findViewById(R.id.etC5);
        etc6 = view.findViewById(R.id.etC6);
        verify_btn = view.findViewById(R.id.verify_btn);
        resend_otp_btn = view.findViewById(R.id.ResendBtn);

        Bundle data = getArguments();
        verification_id = data.getString("verification_code");
        user_phone_no = data.getString("phone_no");
        phoneNo.setText(String.format ("+91-%s",user_phone_no));

        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            verifyNow();
            }
        });

        resend_otp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Forgot_Pass_Frag forgot_pass_frag = new Forgot_Pass_Frag();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.login_interface,forgot_pass_frag);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

      edTextInput();
        return view;
    }
    public void verifyNow()
    {
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Verifying...");
        dialog.setCancelable(false);
        String et1 = etc1.getText().toString().trim();
        String et2 = etc2.getText().toString().trim();
        String et3 = etc3.getText().toString().trim();
        String et4 = etc4.getText().toString().trim();
        String et5 = etc5.getText().toString().trim();
        String et6 = etc6.getText().toString().trim();

        if(et1.isEmpty() || et2.isEmpty() || et3.isEmpty() || et4.isEmpty() || et5.isEmpty() || et6.isEmpty())
        {
            Toast.makeText(getContext(), "Please filled otp", Toast.LENGTH_SHORT).show();
        }
        else if(verification_id != null)
        {
            dialog.show();
            String code = et1+et2+et3+et4+et5+et6;
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verification_id, code);
            FirebaseAuth
                    .getInstance()
                    .signInWithCredential(credential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                dialog.dismiss();
                                Bundle data = new Bundle();
                                data.putString("phone_no",user_phone_no);
                                Set_New_Password_frag set_new_password_frag = new Set_New_Password_frag();
                                set_new_password_frag.setArguments(data);
                                FragmentTransaction transaction =  getParentFragmentManager().beginTransaction();
                                transaction.replace(R.id.login_interface,set_new_password_frag);
                                transaction.commit();
                            } else {
                                dialog.dismiss();
                                Toast.makeText(getContext(), "Invalid OTP", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else
        {
            dialog.dismiss();
            Toast.makeText(getContext(), "Invalid OTP", Toast.LENGTH_SHORT).show();
        }
    }

    public void edTextInput()
    {
       etc1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etc2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etc2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etc3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etc3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               etc4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etc4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               etc5.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etc5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               etc6.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
       });
    }
}