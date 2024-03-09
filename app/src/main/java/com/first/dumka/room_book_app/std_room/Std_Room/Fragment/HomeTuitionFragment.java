package com.first.dumka.room_book_app.std_room.Std_Room.Fragment;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.first.dumka.room_book_app.std_room.R;
import com.first.dumka.room_book_app.std_room.Std_Room.Adapters.Lodge_Img_Slider;
import com.first.dumka.room_book_app.std_room.Std_Room.Modal_Class.Lodge_Img_Slider_Class;
import com.first.dumka.room_book_app.std_room.Std_Room.Modal_Class.Slider_Img_Class;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.first.dumka.room_book_app.std_room.R.id.action_homeTuitionFragment_to_teacherListFragment;

public class HomeTuitionFragment extends Fragment {

    View view;
    CardView parent,teacher;
    String home_tuition_page_url = "http://stdroom.in/Android_khokan/home_tuition_page_img.php";
    private Lodge_Img_Slider lodge_img_slider;
    private SliderView sliderView;
    public HomeTuitionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_home_tuition, container, false);
        sliderView = view.findViewById(R.id.imageSlider);
        parent = view.findViewById(R.id.parent);
        teacher = view.findViewById(R.id.teacher);

        homeTutionHomePageImg();

        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  Navigation.findNavController(view).navigate(action_homeTuitionFragment_to_teacherListFragment);
                // Navigation.findNavController(view).navigate(action_homeTuitionFragment_to_teacherListFragment);
            }
        });

        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  Navigation.findNavController(view).navigate(R.id.action_homeTuitionFragment_to_teacherLogingFragment);

            }
        });
       return  view;
    }

    public void homeTutionHomePageImg()
    {
        StringRequest request = new StringRequest(Request.Method.POST,home_tuition_page_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("Home_Tuition_Page_Img");

                    if (success.equals("1")) {
//                        shimmerFrameLayout.stopShimmer();
//                        shimmerFrameLayout.hideShimmer();
//                        shimmerFrameLayout.setVisibility(View.GONE);
//                        home_page_design.setVisibility(View.VISIBLE);


                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject2 = jsonArray.getJSONObject(0);

                           String   img1 = jsonObject2.getString("Img1");
                           String img2  = jsonObject2.getString("Img2");
                           String img3 = jsonObject2.getString("Img3");

                            String [] images = new String[]{img1,img2,img3};
                            ArrayList<Lodge_Img_Slider_Class> img_list = new ArrayList<>();
                            for(int j = 0;j<images.length;j++)
                            {
                                Lodge_Img_Slider_Class lodge_img_slider_class = new Lodge_Img_Slider_Class(images[j]);
                                img_list.add(lodge_img_slider_class);

                            }
                            lodge_img_slider = new Lodge_Img_Slider(getContext(),img_list);
                            sliderView.setSliderAdapter(lodge_img_slider);

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

                Toast.makeText(getContext(), "No Internet Connection!", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

    }
}