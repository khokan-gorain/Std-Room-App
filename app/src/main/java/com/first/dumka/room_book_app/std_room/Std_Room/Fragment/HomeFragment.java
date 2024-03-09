package com.first.dumka.room_book_app.std_room.Std_Room.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.first.dumka.room_book_app.std_room.R;
import com.first.dumka.room_book_app.std_room.Std_Room.Activityes.Boys_Lodge_Activity;
import com.first.dumka.room_book_app.std_room.Std_Room.Activityes.Girls_Lodge_Activity;
import com.first.dumka.room_book_app.std_room.Std_Room.Activityes.HomeTuitionActivity;
import com.first.dumka.room_book_app.std_room.Std_Room.Activityes.HomeTuitionPayment;
import com.first.dumka.room_book_app.std_room.Std_Room.Adapters.SliderAdapter;
import com.first.dumka.room_book_app.std_room.Std_Room.Modal_Class.Slider_Img_Class;
import com.first.dumka.room_book_app.std_room.databinding.FragmentHomeBinding;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;
    public String[] images;
    private SliderAdapter sliderAdapter;
    private SliderView sliderView;
    ShimmerFrameLayout shimmerFrameLayout;
    LinearLayout home_layout_design,homepage_hide;
    CardView boys_lodge,girls_lodge,home_tuition,flat;
    String home_page_img_url = "http://stdroom.in/Android_khokan/home_page_img.php";
    String img1,img2,img3;
    Button rety_btn_net;
    AlertDialog dialog;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sliderView = root.findViewById(R.id.imageSlider);
        shimmerFrameLayout = root.findViewById(R.id.shimmer_view_contner);
        home_layout_design = root.findViewById(R.id.home_layout_design);
        boys_lodge = root.findViewById(R.id.boys_lodge);
        girls_lodge =root.findViewById(R.id.girls_lodge);
        home_tuition = root.findViewById(R.id.home_tuition);
        flat = root.findViewById(R.id.flat);

        homePageImg();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View layout_dialog = LayoutInflater.from(getContext()).inflate(R.layout.no_internet_connection,null);
        rety_btn_net = layout_dialog.findViewById(R.id.retry_btn);
        builder.setView(layout_dialog);
        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.getWindow().setGravity(Gravity.CENTER);

        rety_btn_net.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homePageImg();
            }
        });

        boys_lodge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Boys_Lodge_Activity.class);
                startActivity(intent);
            }
        });

        girls_lodge.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getContext(),Girls_Lodge_Activity.class);
                startActivity(intent1);
            }
        });

        home_tuition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), HomeTuitionActivity.class);
                startActivity(intent);
            }
        });
        flat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Coming soon!... ", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void checkConnection()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile_network = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifi.isConnected())
        {
          dialog.dismiss();
        }
        else if(mobile_network.isConnected())
        {
         dialog.dismiss();
        }
        else
        {
              dialog.show();
        }

    }

    public void homePageImg()
    {

        StringRequest request = new StringRequest(Request.Method.POST,home_page_img_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("Home_Page_Img");

                    if (success.equals("1")) {
                        dialog.dismiss();
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.hideShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        home_layout_design.setVisibility(View.VISIBLE);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject2 = jsonArray.getJSONObject(0);

                            img1 = jsonObject2.getString("Img1");
                            img2  = jsonObject2.getString("Img2");
                            img3 = jsonObject2.getString("Img3");

                            images = new String[]{img1,img2,img3};
                            ArrayList<Slider_Img_Class> img_list = new ArrayList<>();
                            for(int j = 0;j<images.length;j++)
                            {
                                Slider_Img_Class slider_img_class = new Slider_Img_Class(images[j]);
                                img_list.add(slider_img_class);
                            }
                            sliderAdapter = new SliderAdapter(getContext(),img_list);
                            sliderView.setSliderAdapter(sliderAdapter);

                            sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                            sliderView.setIndicatorAnimation(IndicatorAnimationType.SLIDE);
                            sliderView.startAutoCycle();

                        }
                    } else {
                        Toast.makeText(getContext() ,"No Connection", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                checkConnection();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);


    }

}