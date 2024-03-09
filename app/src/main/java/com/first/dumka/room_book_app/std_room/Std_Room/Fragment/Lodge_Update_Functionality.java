package com.first.dumka.room_book_app.std_room.Std_Room.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import java.util.HashMap;
import java.util.Map;


public class Lodge_Update_Functionality extends Fragment {

    TextView single_bed,double_bed,triple_bed,fourth_bed,lodge_name,lodge_owner_name;
    EditText sin_total_room,sin_available_room,sin_available_bed,sin_room_rent;
    EditText dou_total_room,dou_available_room,dou_available_bed,dou_room_rent;
    EditText tri_total_room,tri_available_room,tri_available_bed,tri_room_rent;
    EditText for_total_room,for_available_room,for_available_bed,for_room_rent;

    LinearLayout single_row,double_row,triple_row,forth_row;

    String Single_bed_room,Sin_total_room,Sin_available_room,Sin_available_bed,Sin_room_rent;
    String Double_bed_room,Dou_total_room,Dou_available_room,Dou_available_bed,Dou_room_rent;
    String Triple_bed_room,Tri_total_room,Tri_available_room,Tri_available_bed,Tri_room_rent;
    String For_bed_room,For_total_room,For_available_room,For_available_bed,For_room_rent;
    Button update_btn;
    String update_url = "http://stdroom.in/Android_khokan/update_room_details.php";
    String lodge_id;


    public Lodge_Update_Functionality() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lodge_update_functionality, container, false);

        single_row = view.findViewById(R.id.single_row);
        double_row = view.findViewById(R.id.double_row);
        triple_row = view.findViewById(R.id.triple_row);
        forth_row = view.findViewById(R.id.fourth_row);

        update_btn = view.findViewById(R.id.update_btn);
        lodge_name = view.findViewById(R.id.lodge_name);
        lodge_owner_name = view.findViewById(R.id.lodge_owner_name);

        single_bed = view.findViewById(R.id.single_bed_room);
        double_bed = view.findViewById(R.id.double_bed_room);
        triple_bed = view.findViewById(R.id.triple_bed_room);
        fourth_bed = view.findViewById(R.id.four_bed_room);

        sin_total_room = view.findViewById(R.id.sin_total_room);
        sin_available_room = view.findViewById(R.id.sin_available_room);
        sin_available_bed = view.findViewById(R.id.sin_available_bed);
        sin_room_rent = view.findViewById(R.id.sin_room_rent);

        dou_total_room = view.findViewById(R.id.dou_total_room);
        dou_available_room = view.findViewById(R.id.dou_available_room);
        dou_available_bed = view.findViewById(R.id.dou_available_bed);
        dou_room_rent = view.findViewById(R.id.dou_room_rent);

        tri_total_room = view.findViewById(R.id.tri_total_room);
        tri_available_room = view.findViewById(R.id.tri_available_room);
        tri_available_bed = view.findViewById(R.id.tri_available_bed);
        tri_room_rent = view.findViewById(R.id.tri_room_rent);

        for_total_room = view.findViewById(R.id.for_total_room);
        for_available_room = view.findViewById(R.id.for_available_room);
        for_available_bed = view.findViewById(R.id.for_available_bed);
        for_room_rent = view.findViewById(R.id.for_room_rent);

        roomDetails();

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updataData();
            }
        });

        return view;
    }



    public void roomDetails()
    {

        Bundle data = getArguments();
        lodge_id = data.getString("Lodge_Id");
       // Toast.makeText(getContext(), lodge_id, Toast.LENGTH_SHORT).show();
        String Lodge_name = data.getString("Lodge_Name");
        String Lodge_owner_name = data.getString("Lodge_Owner_Name");
        lodge_name.setText(Lodge_name);
        lodge_owner_name.setText(Lodge_owner_name);


        Single_bed_room = data.getString("Single_Bed_Room");
        if(Single_bed_room.equals("No"))
        {
            single_row.setVisibility(View.GONE);
        }
        else
        {

            Sin_total_room = data.getString("Sig_Total_Room");
            Sin_available_room = data.getString("Sig_Available_Room");
            Sin_available_bed = data.getString("Sig_Available_Bed");
            Sin_room_rent = data.getString("Sig_Room_Rent");

            single_bed.setText(Single_bed_room);
            sin_total_room.setText(Sin_total_room);
            sin_available_room.setText(Sin_available_room);
            sin_available_bed.setText(Sin_available_bed);
            sin_room_rent.setText(Sin_room_rent);

        }

        Double_bed_room = data.getString("Double_Bed_Room");
        if(Double_bed_room.equals("No"))
        {

            double_row.setVisibility(View.GONE);

        }
        else
        {

            Dou_total_room = data.getString("Dou_Total_Room");
            Dou_available_room = data.getString("Dou_Available_Room");
            Dou_available_bed = data.getString("Dou_Available_Bed");
            Dou_room_rent = data.getString("Dou_Room_Rent");

            double_bed.setText(Double_bed_room);
            dou_total_room.setText(Dou_total_room);
            dou_available_room.setText(Dou_available_room);
            dou_available_bed.setText(Dou_available_bed);
            dou_room_rent.setText(Dou_room_rent);

        }

        Triple_bed_room = data.getString("Triple_Bed_Room");
        if(Triple_bed_room.equals("No"))
        {
            triple_row.setVisibility(View.GONE);

        }
        else
        {

            Tri_total_room = data.getString("Tri_Total_Room");
            Tri_available_room = data.getString("Tri_Available_Room");
            Tri_available_bed = data.getString("Tri_Available_Bed");
            Tri_room_rent = data.getString("Tri_Room_Rent");

            triple_bed.setText(Triple_bed_room);
            tri_total_room.setText(Tri_total_room);
            tri_available_room.setText(Tri_available_room);
            tri_available_bed.setText(Tri_available_bed);
            tri_room_rent.setText(Tri_room_rent);

        }

        For_bed_room = data.getString("For_Bed_Room");
        if(For_bed_room.equals("No"))
        {
            forth_row.setVisibility(View.GONE);

        }
        else
        {

            For_total_room = data.getString("For_Bed_Room");
            For_available_room = data.getString("For_Available_Room");
            For_available_bed = data.getString("For_Available_Bed");
            For_room_rent = data.getString("For_Room_Rent");

            fourth_bed.setText(For_bed_room);
            for_total_room.setText(For_total_room);
            tri_available_room.setText(For_available_room);
            tri_available_bed.setText(For_available_bed);
            tri_room_rent.setText(For_room_rent);


        }


    }

    public void updataData()
    {
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Updating...");
        dialog.setCancelable(false);
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, update_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String name = response.trim();
                if (name.equals("success")) {
                        dialog.dismiss();
                        Toast.makeText(getContext(), "Data update successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(getContext(), "Sorry! Data not updated", Toast.LENGTH_SHORT).show();
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();

                param.put("Lodge_Id",lodge_id);

                if(Single_bed_room.equals("No"))
                {
                    param.put("Single_bed_room", "No");
                    param.put("Sin_total_room", "0");
                    param.put("Sin_available_room","0");
                    param.put("Sin_available_bed", "0");
                    param.put("Sin_room_rent", "0");
                } else {
                    param.put("Single_bed_room", single_bed.getText().toString());
                    param.put("Sin_total_room", sin_total_room.getText().toString());
                    param.put("Sin_available_room", sin_available_room.getText().toString());
                    param.put("Sin_available_bed", sin_available_bed.getText().toString());
                    param.put("Sin_room_rent", sin_room_rent.getText().toString());
                }

                if(Double_bed_room.equals("No"))
                {
                    param.put("Double_bed_room","No");
                    param.put("Dou_total_room","0");
                    param.put("Dou_available_room","0");
                    param.put("Dou_available_bed","0");
                    param.put("Dou_room_rent","0");
                } else {
                    param.put("Double_bed_room",double_bed.getText().toString());
                    param.put("Dou_total_room",dou_total_room.getText().toString());
                    param.put("Dou_available_room",dou_available_room.getText().toString());
                    param.put("Dou_available_bed",dou_available_bed.getText().toString());
                    param.put("Dou_room_rent",dou_room_rent.getText().toString());
                }

                if(Triple_bed_room.equals("No")){
                    param.put("Triple_bed_room","No");
                    param.put("Tri_total_room","0");
                    param.put("Tri_available_room","0");
                    param.put("Tri_available_bed","0");
                    param.put("Tri_room_rent","0");
                }
                 else
                {
                    param.put("Triple_bed_room",triple_bed.getText().toString());
                    param.put("Tri_total_room",tri_total_room.getText().toString());
                    param.put("Tri_available_room",tri_available_room.getText().toString());
                    param.put("Tri_available_bed",tri_available_bed.getText().toString());
                    param.put("Tri_room_rent",tri_room_rent.getText().toString());
                }

                if(For_bed_room.equals("No")){
                    param.put("For_bed_room","No");
                    param.put("For_total_room","0");
                    param.put("For_available_room","0");
                    param.put("For_available_bed","0");
                    param.put("For_room_rent","0");
                } else
                {
                    param.put("For_bed_room",fourth_bed.getText().toString());
                    param.put("For_total_room",for_total_room.getText().toString());
                    param.put("For_available_room",for_available_room.getText().toString());
                    param.put("For_available_bed",for_available_bed.getText().toString());
                    param.put("For_room_rent",for_room_rent.getText().toString());
                }


                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }
}