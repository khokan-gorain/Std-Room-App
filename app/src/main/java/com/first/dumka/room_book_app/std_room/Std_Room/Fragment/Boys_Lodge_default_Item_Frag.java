package com.first.dumka.room_book_app.std_room.Std_Room.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.first.dumka.room_book_app.std_room.Std_Room.Activityes.Boys_Lodge_Activity;
import com.first.dumka.room_book_app.std_room.Std_Room.Activityes.Separate_Lodge_Details;
import com.first.dumka.room_book_app.std_room.Std_Room.Adapters.Single_Lodge_Item_Info_Ad;
import com.first.dumka.room_book_app.std_room.Std_Room.Modal_Class.Single_Lodge_Item_Info;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Boys_Lodge_default_Item_Frag extends Fragment {

    String select_location;
    RecyclerView boys_lodge_default_item;
    ShimmerFrameLayout shimmerFrameLayout;
    Single_Lodge_Item_Info single_lodge_item_info;
    Single_Lodge_Item_Info_Ad single_lodge_item_info_ad;
    ArrayList<Single_Lodge_Item_Info> arrayList = new ArrayList<>();
    String url = "http://stdroom.in/Android_khokan/bl_withlocation.php";
    String bed;
    public Boys_Lodge_default_Item_Frag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_boys_lodge_default_item, container, false);

        boys_lodge_default_item = view.findViewById(R.id.boys_lodge_default_item);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_effect);

        retriveData();
        single_lodge_item_info_ad = new Single_Lodge_Item_Info_Ad(getContext(), arrayList,new Single_Lodge_Item_Info_Ad.ItemClickListener() {
            @Override
            public void onItemClick(Single_Lodge_Item_Info single_lodge_item_info) {
                String lodge_id = single_lodge_item_info.getLodge_id();

                Intent intent = new Intent(getContext(), Separate_Lodge_Details.class);
                intent.putExtra("lodge_id",lodge_id);
                startActivity(intent);

            }
        });
        boys_lodge_default_item.setAdapter(single_lodge_item_info_ad);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        boys_lodge_default_item.setLayoutManager(linearLayoutManager);

        return view;
    }

    public void retriveData() {
        Bundle data = getArguments();

        if (data != null) {
            select_location = data.getString("location");
        }
        else
        {
            Intent intent = new Intent(getContext(),Boys_Lodge_Activity.class);
            startActivity(intent);
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                arrayList.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("BL_Card_Item");

                    if(jsonArray.length() == 0) {
                        Bundle data = new Bundle();
                        data.putString("msg", "Lodge Not Found!");
                        Lodge_Not_Found_Frag lodge_not_found_frag = new Lodge_Not_Found_Frag();
                        lodge_not_found_frag.setArguments(data);
                        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_replace_layout, lodge_not_found_frag);
                        transaction.commit();

                    }


                        if (success.equals("1")) {
                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);
                            boys_lodge_default_item.setVisibility(View.VISIBLE);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                String lodge_id = jsonObject1.getString("Lodge_Id");
                                String lodge_name = jsonObject1.getString("Lodge_Name");
                                String single_bed = jsonObject1.getString("Sig_Available_Bed");
                                String double_bed = jsonObject1.getString("Dou_Available_Bed");
                                String triple_bed = jsonObject1.getString("Tri_Available_Bed");
                                String four_bed = jsonObject1.getString("For_Available_Bed");
                                String bed_available = jsonObject1.getString("Lodge_Area");
                                String lodge_location = jsonObject1.getString("Lodge_Location");
                                String lodge_img = jsonObject1.getString("Lodge_Img");

                                if (single_bed.equals("0") && double_bed.equals("0") && triple_bed.equals("0") && four_bed.equals("0")) {
                                    bed = "Bed Unavailable";
                                } else {
                                    bed = "Bed Available";
                                }

                                single_lodge_item_info = new Single_Lodge_Item_Info(lodge_name, lodge_location, bed_available, lodge_img, lodge_id, bed);
                                arrayList.add(single_lodge_item_info);
                                single_lodge_item_info_ad.notifyDataSetChanged();

                            }
                        } else {
                            // shimmerFrameLayout.setVisibility(View.GONE);
                        }

                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No internet connection", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<String, String>();
                param.put("select_location", select_location);
                param.put("lodge_type","Boys Lodge");
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}