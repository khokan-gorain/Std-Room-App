package com.first.dumka.room_book_app.std_room.Std_Room.Activityes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
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
import com.facebook.shimmer.ShimmerFrameLayout;
import com.first.dumka.room_book_app.std_room.R;
import com.first.dumka.room_book_app.std_room.Std_Room.Adapters.Lodge_Img_Slider;
import com.first.dumka.room_book_app.std_room.Std_Room.Adapters.Nareby_Area_Ad;
import com.first.dumka.room_book_app.std_room.Std_Room.Adapters.Rule_And_Regulation_Ad;
import com.first.dumka.room_book_app.std_room.Std_Room.Adapters.boys_Lodge_Facilities_Ad;
import com.first.dumka.room_book_app.std_room.Std_Room.Modal_Class.Lodge_Img_Slider_Class;
import com.first.dumka.room_book_app.std_room.Std_Room.Modal_Class.Nearby_Area;
import com.first.dumka.room_book_app.std_room.Std_Room.Modal_Class.RuleAndRegulation;
import com.first.dumka.room_book_app.std_room.Std_Room.Modal_Class.boys_lodge_facilities;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Separate_Lodge_Details extends AppCompatActivity {

    RecyclerView boys_lodge_facilities_recview, nearby_recyclerview,rule_and_regulation_rec;
    boys_lodge_facilities boys_lodge_facilities;
    ShimmerFrameLayout shimmerFrameLayout;
    LinearLayout separate_lodge_visible,single_row,double_row,triple_row,four_row;
    String lodge_id,phone_no1,phone_no2;
    String topviewurl = "http://stdroom.in/Android_khokan/bl_separate_topview.php";
    String bl_facilities_url = "http://stdroom.in/Android_khokan/bl_facilities.php";
    String bl_what_nearby_url = "http://stdroom.in/Android_khokan/bl_what_nearby.php";
    String bl_room_details  = "http://stdroom.in/Android_khokan/bl_room_details.php";
    String rule_and_regulation_url = "http://stdroom.in/Android_khokan/rule_and_regulation.php";
    TextView lodge_name, lodge_location, lodge_dis;
    TextView single_bed_room,sig_total_room,sig_available_room,sig_available_bed,sig_room_rent;
    TextView double_bed_room,dou_total_room,dou_available_room,dou_available_bed,dou_room_rent;
    TextView triple_bed_room,tri_total_room,tri_available_room,tri_available_bed,tri_room_rent;
    TextView four_bed_room,for_total_room,for_available_room,for_available_bed,for_room_rent;
    TextView total_room_rent,bed_type;
    Button call_us_to_book;
    static int PERMISSION_CODE= 100;
    private SliderView sliderView;
    String faci_name[];
    String nearby_name[];
    String rule_and_regu[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_separate_lodge_details);
        boys_lodge_facilities_recview = findViewById(R.id.facilities_recyclerview);
        nearby_recyclerview = findViewById(R.id.nearby_recyclerview);
        rule_and_regulation_rec = findViewById(R.id.rule_and_regulation);
        shimmerFrameLayout = findViewById(R.id.separate_lodge_effect);
        separate_lodge_visible = findViewById(R.id.separate_lodge_visible);
        call_us_to_book = findViewById(R.id.call_us_to_book);

        lodge_name = findViewById(R.id.sep_lodge_name);
        lodge_location = findViewById(R.id.sep_lodge_location);
        lodge_dis = findViewById(R.id.sep_lodge_dis);
        total_room_rent = findViewById(R.id.total_room_rent);
        bed_type = findViewById(R.id.bed_type);

        single_bed_room = findViewById(R.id.single_bed_room);
        sig_total_room = findViewById(R.id.sin_total_room);
        sig_available_room = findViewById(R.id.sin_available_room);
        sig_available_bed = findViewById(R.id.sin_available_bed);
        sig_room_rent = findViewById(R.id.sin_room_rent);

        double_bed_room = findViewById(R.id.double_bed_room);
        dou_total_room = findViewById(R.id.dou_total_room);
        dou_available_room = findViewById(R.id.dou_available_room);
        dou_available_bed = findViewById(R.id.dou_available_bed);
        dou_room_rent = findViewById(R.id.dou_room_rent);

        triple_bed_room = findViewById(R.id.triple_bed_room);
        tri_total_room = findViewById(R.id.tri_total_room);
        tri_available_room = findViewById(R.id.tri_available_room);
        tri_available_bed = findViewById(R.id.tri_available_bed);
        tri_room_rent = findViewById(R.id.tri_room_rent);

        four_bed_room = findViewById(R.id.four_bed_room);
        for_total_room = findViewById(R.id.for_total_room);
        for_available_room = findViewById(R.id.for_available_room);
        for_available_bed = findViewById(R.id.for_available_bed);
        for_room_rent = findViewById(R.id.for_room_rent);

        single_row = findViewById(R.id.single_row);
        double_row = findViewById(R.id.double_row);
        triple_row = findViewById(R.id.triple_row);
        four_row = findViewById(R.id.four_row);
        sliderView = findViewById(R.id.lodge_images);


        Bundle bundle = getIntent().getExtras();
        lodge_id = bundle.getString("lodge_id");



        boysLodgeFacilities();
        whatIsNearBy();
        lodgeTopView();
        roomDetails();
        callUsToBook();
        ruleAanRegulation();


    }

    public void lodgeTopView() {
        StringRequest request = new StringRequest(Request.Method.POST, topviewurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("BL_Separate_TopView");


                    if (success.equals("1")) {

                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        separate_lodge_visible.setVisibility(View.VISIBLE);


                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                            String lod_name = jsonObject1.getString("Lodge_Name");
                            getSupportActionBar().setTitle(lod_name);
                            String lod_location = jsonObject1.getString("Lodge_Location");
                            String lod_dis = jsonObject1.getString("Lodge_Discription");
                            String lod_img1 = jsonObject1.getString("Lodge_Img1");
                            String lod_img2 = jsonObject1.getString("Lodge_Img2");
                            String lod_img3 = jsonObject1.getString("Lodge_Img3");
                             phone_no1 = jsonObject1.getString("Phone_No1");
                             phone_no2 = jsonObject1.getString("Phone_No2");
                            lodge_name.setText(lod_name);
                            lodge_location.setText(lod_location);
                            lodge_dis.setText(lod_dis);

                            String lodge_img [] = new String[]{lod_img1,lod_img2,lod_img3};
                           ArrayList<Lodge_Img_Slider_Class> img_list = new ArrayList<>();
                           for(int j=0;j<lodge_img.length;j++)
                           {
                               Lodge_Img_Slider_Class lodge_img_slider_class = new Lodge_Img_Slider_Class(lodge_img[j]);
                               img_list.add(lodge_img_slider_class);
                           }
                            Lodge_Img_Slider lodge_img_slider = new Lodge_Img_Slider(Separate_Lodge_Details.this,img_list);
                            sliderView.setSliderAdapter(lodge_img_slider);

                            sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                            sliderView.setIndicatorAnimation(IndicatorAnimationType.SLIDE);
                            sliderView.startAutoCycle();

                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("lodge_id", lodge_id);
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void whatIsNearBy() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, bl_what_nearby_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("BL_Whats_Nearby");


                    if (success.equals("1")) {


                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject2 = jsonArray.getJSONObject(0);

                            String nearby1 = jsonObject2.getString("Nearby1");
                            String nearby2 = jsonObject2.getString("Nearby2");
                            String nearby3  = jsonObject2.getString("Nearby3");
                            String nearby4 = jsonObject2.getString("Nearby4");
                            String nearby5 = jsonObject2.getString("Nearby5");
                            String nearby6 = jsonObject2.getString("Nearby6");
                            String nearby7 = jsonObject2.getString("Nearby7");
                            String nearby8 = jsonObject2.getString("Nearby8");
                            String nearby9 = jsonObject2.getString("Nearby9");
                            String nearby10 = jsonObject2.getString("Nearby10");

                             nearby_name = new String[]{nearby1,nearby2,nearby3,nearby4,nearby5,nearby6,nearby7,nearby9,nearby10};

                            if(nearby10.isEmpty()){
                                 nearby_name = new String[]{nearby1,nearby2,nearby3,nearby4,nearby5,nearby6,nearby7,nearby8,nearby9};
                            }
                             if(nearby9.isEmpty())
                            {
                                nearby_name = new String[]{nearby1,nearby2,nearby3,nearby4,nearby5,nearby6,nearby7,nearby8};
                            }
                            if (nearby8.isEmpty())
                            {
                                nearby_name = new String[]{nearby1,nearby2,nearby3,nearby4,nearby5,nearby6,nearby7};
                            }
                            if (nearby7.isEmpty())
                            {
                                nearby_name = new String[]{nearby1,nearby2,nearby3,nearby4,nearby5,nearby6};
                            }
                            if (nearby6.isEmpty())
                            {
                                nearby_name = new String[]{nearby1,nearby2,nearby3,nearby4,nearby5};
                            }
                            if (nearby5.isEmpty())
                            {
                                nearby_name = new String[]{nearby1,nearby2,nearby3,nearby4};
                            }
                            if (nearby4.isEmpty())
                            {
                                nearby_name = new String[]{nearby1,nearby2,nearby3};
                            }
                            if (nearby3.isEmpty())
                            {
                                nearby_name = new String[]{nearby1,nearby2};
                            }
                            if (nearby2.isEmpty())
                            {
                                nearby_name = new String[]{nearby1};
                            }
                            if (nearby1.isEmpty())
                            {
                                nearby_name = new String[]{};
                            }


                            String dis1 = jsonObject2.getString("Dis1");
                            String dis2 = jsonObject2.getString("Dis2");
                            String dis3 = jsonObject2.getString("Dis3");
                            String dis4 = jsonObject2.getString("Dis4");
                            String dis5 = jsonObject2.getString("Dis5");
                            String dis6 = jsonObject2.getString("Dis6");
                            String dis7 = jsonObject2.getString("Dis7");
                            String dis8 = jsonObject2.getString("Dis8");
                            String dis9 = jsonObject2.getString("Dis9");
                            String dis10 = jsonObject2.getString("Dis10");
                            String [] nearby_distance = new String[]{dis1,dis2,dis3,dis4,dis5,dis6,dis7,dis8,dis9,dis10};

                            ArrayList<Nearby_Area> nearby_list = new ArrayList<>();


                            for (int j= 0; j < nearby_name.length; j++) {
                                Nearby_Area area = new Nearby_Area(nearby_name[j],nearby_distance[j]);
                                nearby_list.add(area);
                            }

                            Nareby_Area_Ad nareby_area_ad = new Nareby_Area_Ad(nearby_list, getApplicationContext());
                            nearby_recyclerview.setAdapter(nareby_area_ad);
                            nareby_area_ad.notifyDataSetChanged();


                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                            nearby_recyclerview.setLayoutManager(linearLayoutManager);
                            nearby_recyclerview.setHasFixedSize(true);


                        }
                    } else {

                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String > param  = new HashMap<>();
                param.put("lodge_id",lodge_id);
                return param;
            }
        };

        RequestQueue requestQueue1 = Volley.newRequestQueue(Separate_Lodge_Details.this);
        requestQueue1.add(stringRequest);



    }

    public void boysLodgeFacilities() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, bl_facilities_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("BL_Facilities");


                    if (success.equals("1")) {



                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject2 = jsonArray.getJSONObject(0);

                            String name1 = jsonObject2.getString("Name1");
                            String name2 = jsonObject2.getString("Name2");
                            String name3  = jsonObject2.getString("Name3");
                            String name4 = jsonObject2.getString("Name4");
                            String name5 = jsonObject2.getString("Name5");
                            String name6 = jsonObject2.getString("Name6");
                            String name7 = jsonObject2.getString("Name7");
                            String name8 = jsonObject2.getString("Name8");
                            String name9 = jsonObject2.getString("Name9");
                            String name10 = jsonObject2.getString("Name10");

                           faci_name = new String[]{name1,name2,name3,name4,name5,name6,name7,name8,name9,name10};

                            if(name10.isEmpty()){
                                 faci_name = new String[]{name1,name2,name3,name4,name5,name6,name7,name8,name9};
                            }
                             if (name9.isEmpty())
                            {
                                faci_name = new String[]{name1,name2,name3,name4,name5,name6,name7,name8};
                            }
                             if(name8.isEmpty()){
                                 faci_name = new String[]{name1,name2,name3,name4,name5,name6,name7};
                            }
                           if(name7.isEmpty())
                            {
                                 faci_name = new String[]{name1,name2,name3,name4,name5,name6};
                            }
                            if (name6.isEmpty())
                            {
                                 faci_name = new String[]{name1,name2,name3,name4,name5};
                            }
                            if(name5.isEmpty())
                            {
                                faci_name = new String[]{name1,name2,name3,name4};
                            }
                            if(name4.isEmpty())
                            {
                                faci_name = new String[]{name1,name2,name3};
                            }
                            if(name3.isEmpty())
                            {
                                faci_name = new String[]{name1,name2};
                            }
                            if(name2.isEmpty())
                            {
                                faci_name = new String[]{name1};
                            }
                            if(name1.isEmpty())
                            {
                                faci_name = new String[]{};
                            }

                            String img1 = jsonObject2.getString("Img1");
                            String img2 = jsonObject2.getString("Img2");
                            String img3 = jsonObject2.getString("Img3");
                            String img4 = jsonObject2.getString("Img4");
                            String img5 = jsonObject2.getString("Img5");
                            String img6 = jsonObject2.getString("Img6");
                            String img7 = jsonObject2.getString("Img7");
                            String img8 = jsonObject2.getString("Img8");
                            String img9 = jsonObject2.getString("Img9");
                            String img10 = jsonObject2.getString("Img10");
                            String [] faci_img = new String[]{img1,img2,img3,img4,img5,img6,img7,img8,img9,img10};

                            ArrayList<boys_lodge_facilities> list = new ArrayList<>();

                            for (int j= 0; j < faci_name.length; j++) {
                                boys_lodge_facilities = new boys_lodge_facilities(faci_name[j], faci_img[j]);
                                list.add(boys_lodge_facilities);
                            }
                            boys_Lodge_Facilities_Ad boys_lodge_facilities_ad = new boys_Lodge_Facilities_Ad(getApplicationContext(), list);
                            boys_lodge_facilities_recview.setAdapter(boys_lodge_facilities_ad);
                            boys_lodge_facilities_ad.notifyDataSetChanged();

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                            boys_lodge_facilities_recview.setLayoutManager(linearLayoutManager);
                            boys_lodge_facilities_recview.setHasFixedSize(true);


                        }
                    } else {

                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String > param  = new HashMap<>();
                param.put("lodge_id",lodge_id);
                return param;
            }
        };

        RequestQueue requestQueue1 = Volley.newRequestQueue(Separate_Lodge_Details.this);
        requestQueue1.add(stringRequest);

    }

    public void roomDetails()
    {
        StringRequest stringRequest4 = new StringRequest(Request.Method.POST, bl_room_details, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray4 = jsonObject.getJSONArray("BL_Room_Details");


                    if (success.equals("1")) {
                        

                        for (int k = 0; k < jsonArray4.length(); k++) {
                            JSONObject jsonObject2 = jsonArray4.getJSONObject(0);

                            String Single_bed_room = jsonObject2.getString("Single_Bed_Room");
                            String Sig_total_room = jsonObject2.getString("Sig_Total_Room");
                            String Sig_available_room  = jsonObject2.getString("Sig_Available_Room");
                            String Sig_available_bed = jsonObject2.getString("Sig_Available_Bed");
                            String Sig_roon_rent = jsonObject2.getString("Sig_Roon_Rent");

                            if(Single_bed_room.equals("No")){
                                single_row.setVisibility(View.GONE);
                            } else
                            {
                                single_bed_room.setText(Single_bed_room);
                                sig_total_room.setText(Sig_total_room);
                                sig_available_room.setText(Sig_available_room);
                                sig_available_bed.setText(Sig_available_bed);
                                sig_room_rent.setText(Sig_roon_rent);
                            }


                            String Double_bed_room = jsonObject2.getString("Double_Bed_Room");
                            String Dou_total_room = jsonObject2.getString("Dou_Total_Room");
                            String Dou_available_room = jsonObject2.getString("Dou_Available_Room");
                            String Dou_available_bed= jsonObject2.getString("Dou_Available_Bed");
                            String Dou_room_rent = jsonObject2.getString("Dou_Room_Rent");

                            if(Double_bed_room.equals("No"))
                            {
                                double_row.setVisibility(View.GONE);
                            }
                            else
                            {
                                double_bed_room.setText(Double_bed_room);
                                dou_total_room.setText(Dou_total_room);
                                dou_available_room.setText(Dou_available_room);
                                dou_available_bed.setText(Dou_available_bed);
                                dou_room_rent.setText(Dou_room_rent);
                            }


                            String Triple_bed_room = jsonObject2.getString("Triple_Bed_Room");
                            String Tri_total_room = jsonObject2.getString("Tri_Total_Room");
                            String Tri_available_room = jsonObject2.getString("Tri_Available_Room");
                            String Tri_available_bed = jsonObject2.getString("Tri_Available_Bed");
                            String Tri_room_rent = jsonObject2.getString("Tri_Room_Rent");

                            if(Triple_bed_room.equals("No")) {
                               triple_row.setVisibility(View.GONE);
                            }
                            else
                            {
                                triple_bed_room.setText(Triple_bed_room);
                                tri_total_room.setText(Tri_total_room);
                                tri_available_room.setText(Tri_available_room);
                                tri_available_bed.setText(Tri_available_bed);
                                tri_room_rent.setText(Tri_room_rent);
                            }

                            String For_bed_room = jsonObject2.getString("For_Bed_Room");
                            String For_total_room = jsonObject2.getString("For_Total_Room");
                            String For_available_room = jsonObject2.getString("For_Available_Room");
                            String For_available_bed = jsonObject2.getString("For_Available_Bed");
                            String For_room_rent = jsonObject2.getString("For_Room_Rent");

                            if(For_bed_room.equals("No")){
                              four_row.setVisibility(View.GONE);
                            }
                            else
                            {
                                four_bed_room.setText(For_bed_room);
                                for_total_room.setText(For_total_room);
                                for_available_room.setText(For_available_room);
                                for_available_bed.setText(For_available_bed);
                                for_room_rent.setText(For_room_rent);
                            }

                            if(!Dou_room_rent.equals("0"))
                            {
                                total_room_rent.setText(Dou_room_rent);
                                //bed_type.setText("Double Bed Room");
                            }
                            else if(!Tri_room_rent.equals("0"))
                            {
                                total_room_rent.setText(Tri_room_rent);
                                //bed_type.setText("Triple Bed room");
                            }
                            else if(!For_room_rent.equals("0"))
                            {
                                total_room_rent.setText(For_room_rent);
                                //bed_type.setText("Fourth Bed Room");
                            }
                           else if(!Sig_roon_rent.equals("0"))
                            {
                                total_room_rent.setText(Sig_roon_rent);
                            }


                        }
                    } else {

                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String > param2  = new HashMap<>();
                param2.put("lodgeid",lodge_id);
                return param2;
            }
        };

        RequestQueue requestQueue2 = Volley.newRequestQueue(Separate_Lodge_Details.this);
        requestQueue2.add(stringRequest4);

    }

    public void ruleAanRegulation(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, rule_and_regulation_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("Rule_And_Regulation");


                    if (success.equals("1")) {


                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject2 = jsonArray.getJSONObject(0);


                            String rule1 = jsonObject2.getString("Rule1");
                            String rule2 = jsonObject2.getString("Rule2");
                            String rule3  = jsonObject2.getString("Rule3");
                            String rule4 = jsonObject2.getString("Rule4");
                            String rule5 = jsonObject2.getString("Rule5");
                            String rule6 = jsonObject2.getString("Rule6");
                            String rule7 = jsonObject2.getString("Rule7");
                            String rule8 = jsonObject2.getString("Rule8");
                            String rule9 = jsonObject2.getString("Rule9");
                            String rule10 = jsonObject2.getString("Rule10");

                            rule_and_regu = new String[]{rule1,rule2,rule3,rule4,rule5,rule6,rule7,rule8,rule9,rule10};


                            if(rule10.isEmpty()){
                                rule_and_regu = new String[]{rule1,rule2,rule3,rule4,rule5,rule6,rule7,rule8,rule9};
                            }
                            if(rule9.isEmpty())
                            {
                                rule_and_regu = new String[]{rule1,rule2,rule3,rule4,rule5,rule6,rule7,rule8};
                            }
                            if (rule8.isEmpty())
                            {
                                rule_and_regu = new String[]{rule1,rule2,rule3,rule4,rule5,rule6,rule7};
                            }
                            if (rule7.isEmpty())
                            {
                                rule_and_regu = new String[]{rule1,rule2,rule3,rule4,rule5,rule6};
                            }
                            if (rule6.isEmpty())
                            {
                                rule_and_regu = new String[]{rule1,rule2,rule3,rule4,rule5};
                            }
                            if (rule5.isEmpty())
                            {
                                rule_and_regu = new String[]{rule1,rule2,rule3,rule4};
                            }
                            if (rule4.isEmpty())
                            {
                                rule_and_regu = new String[]{rule1,rule2,rule3};
                            }
                            if (rule3.isEmpty())
                            {
                                rule_and_regu = new String[]{rule1,rule2};
                            }
                            if (rule2.isEmpty())
                            {
                                rule_and_regu = new String[]{rule1};
                            }
                            if (rule1.isEmpty())
                            {
                                rule_and_regu = new String[]{};
                            }


                            ArrayList<RuleAndRegulation> rule_list = new ArrayList<>();


                            for (int j= 0; j < rule_and_regu.length; j++) {
                                RuleAndRegulation rule = new RuleAndRegulation(rule_and_regu[j]);
                                rule_list.add(rule);
                            }

                            Rule_And_Regulation_Ad rule_and_regulation_ad = new Rule_And_Regulation_Ad(rule_list,getApplicationContext());
                            rule_and_regulation_rec.setAdapter(rule_and_regulation_ad);
                            rule_and_regulation_ad.notifyDataSetChanged();


                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                            rule_and_regulation_rec.setLayoutManager(linearLayoutManager);
                            rule_and_regulation_rec.setHasFixedSize(true);


                        }
                    } else {

                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String > param  = new HashMap<>();
                param.put("lodge_id",lodge_id);
                return param;
            }
        };

        RequestQueue requestQueue1 = Volley.newRequestQueue(Separate_Lodge_Details.this);
        requestQueue1.add(stringRequest);


    }
    public void callUsToBook()
    {

        call_us_to_book.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                Dexter.withContext(getApplicationContext())
                      .withPermission(Manifest.permission.CALL_PHONE)
                      .withListener(new PermissionListener() {
                          @Override
                          public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                              Intent i = new Intent(Intent.ACTION_CALL);
                              i.setData(Uri.parse("tel:"+phone_no1));
                              startActivity(i);
                          }

                          @Override
                          public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                              Intent intent = new Intent();
                              intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                              Uri uri = Uri.fromParts("package",getPackageName(),null);
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

}