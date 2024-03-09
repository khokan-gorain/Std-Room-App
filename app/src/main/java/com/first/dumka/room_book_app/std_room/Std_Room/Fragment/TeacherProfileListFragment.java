package com.first.dumka.room_book_app.std_room.Std_Room.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.first.dumka.room_book_app.std_room.Std_Room.Adapters.TeacherListAdapter;
import com.first.dumka.room_book_app.std_room.Std_Room.Modal_Class.Single_Lodge_Item_Info;
import com.first.dumka.room_book_app.std_room.Std_Room.Modal_Class.TeacherList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.first.dumka.room_book_app.std_room.R.id.action_teacherListFragment_to_teacherProfileFragment;

public class TeacherProfileListFragment extends Fragment {

    RecyclerView teacher_list_recview;
    ShimmerFrameLayout shimmerFrameLayout;
    TeacherListAdapter teacherListAdapter;
    LinearLayout fragment_replace_layout;
    ArrayList<TeacherList> teacher_list = new ArrayList<>();
    private String teacher_list_url = "http://stdroom.in/Android_khokan/teachers_list.php";
    private String teacherlistwithgenderurl = "http://stdroom.in/Android_khokan/teacherlistwithgender.php";
    String user_select_gender;

    public TeacherProfileListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_teacher_profile_list, container, false);

        teacher_list_recview = view.findViewById(R.id.teacher_list_recview);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_effect);
        fragment_replace_layout = view.findViewById(R.id.fragment_replace_layout);



        Bundle data = getArguments();

        if(data != null)
        {
            user_select_gender = data.getString("user_select_gender");
            if(user_select_gender.equals("Male") || user_select_gender.equals("Female")) {
                fetchTeacherDataWithGender();
            }
            else
            {
                fetchTeacherData();
            }
        }
        else
        {
            fetchTeacherData();
        }



        teacherListAdapter = new TeacherListAdapter(teacher_list, getContext(), new TeacherListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(TeacherList teacherList) {
                String teacher_id = teacherList.getTech_id();
                Bundle thrdata = new Bundle();
                thrdata.putString("teacher_id",teacher_id);

                Navigation.findNavController(view).navigate(action_teacherListFragment_to_teacherProfileFragment,thrdata);

            }
        });


        return view;
    }

    public void fetchTeacherData()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, teacher_list_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                teacher_list.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("Teacher_List");

                    if(jsonArray.length() == 0)
                    {
                        Toast.makeText(getContext(), "Teacher Not Found!", Toast.LENGTH_LONG).show();

                    }


                    if (success.equals("1")) {
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        teacher_list_recview.setVisibility(View.VISIBLE);


                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String teacher_id = jsonObject1.getString("id");
                            String teacher_name = jsonObject1.getString("teacher_name");
                            String teacher_qualification = jsonObject1.getString("qualification");
                            String teacher_specialization = jsonObject1.getString("specialization");
                            String teacher_img = jsonObject1.getString("img");
                            String teacher_status = jsonObject1.getString("status");

                            TeacherList teacherList = new TeacherList(teacher_id,teacher_name,teacher_qualification,teacher_specialization,teacher_status,teacher_img);
                            teacher_list.add(teacherList);
                            teacher_list_recview.setAdapter(teacherListAdapter);

                             LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                             teacher_list_recview.setLayoutManager(linearLayoutManager);
                             teacherListAdapter.notifyDataSetChanged();

                        }
                    } else {
                        Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void fetchTeacherDataWithGender(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, teacherlistwithgenderurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                teacher_list.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("Teacher_List");

                    if(jsonArray.length() == 0)
                    {
                        Toast.makeText(getContext(), "Teacher Not Found!", Toast.LENGTH_LONG).show();

                    }


                    if (success.equals("1")) {
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        teacher_list_recview.setVisibility(View.VISIBLE);


                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String teacher_id = jsonObject1.getString("id");
                            String teacher_name = jsonObject1.getString("teacher_name");
                            String teacher_qualification = jsonObject1.getString("qualification");
                            String teacher_specialization = jsonObject1.getString("specialization");
                            String teacher_img = jsonObject1.getString("img");
                            String teacher_status = jsonObject1.getString("status").trim();

                            TeacherList teacherList = new TeacherList(teacher_id,teacher_name,teacher_qualification,teacher_specialization,teacher_status,teacher_img);
                            teacher_list.add(teacherList);
                            teacher_list_recview.setAdapter(teacherListAdapter);

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                            teacher_list_recview.setLayoutManager(linearLayoutManager);
                            teacherListAdapter.notifyDataSetChanged();

                        }
                    } else {
                        Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("user_select_gender",user_select_gender);
                return hashMap;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}
