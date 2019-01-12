package com.foodx.nsh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.foodx.nsh.R;
import com.foodx.nsh.adapter.CartAdapter;
import com.foodx.nsh.adapter.MenuAdapter;
import com.foodx.nsh.model.Item;
import com.foodx.nsh.model.Menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuActivity extends AppCompatActivity {

    private TextView hotelName;
    private RecyclerView recyclerView;
    private MenuAdapter menuAdapter;
    private List<Menu> menuList;
    String id;
    String mere;
    JSONArray array;

    private String hotelid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        onInit();
    }

    public void onInit() {
        hotelName = findViewById(R.id.hotel_name);
        recyclerView = findViewById(R.id.recyclerView);
        Bundle bundle = getIntent().getExtras();
//        cardView.setCardBackgroundColor(getResources().getColor(bundle.getInt("color")));
        hotelName.setText(bundle.getString("name"));
        id = bundle.getString("id");
        menuList = new ArrayList<>();
        menuAdapter = new MenuAdapter(bundle.getInt("color"), menuList, this,id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        hotelid = bundle.getString("id");
        menuList = new ArrayList<>();
        menuAdapter = new MenuAdapter(bundle.getInt("color"),menuList, this,hotelid);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(menuAdapter);
        LinearSnapHelper snapHelper = new LinearSnapHelper() {
            @Override
            public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
                View centerView = findSnapView(layoutManager);
                if (centerView == null)
                    return RecyclerView.NO_POSITION;

                int position = layoutManager.getPosition(centerView);
                int targetPosition = -1;
                if (layoutManager.canScrollHorizontally()) {
                    if (velocityX < 0) {
                        targetPosition = position - 1;
                    } else {
                        targetPosition = position + 1;
                    }
                }

                if (layoutManager.canScrollVertically()) {
                    if (velocityY < 0) {
                        targetPosition = position - 1;
                    } else {
                        targetPosition = position + 1;
                    }
                }

                final int firstItem = 0;
                final int lastItem = layoutManager.getItemCount() - 1;
                targetPosition = Math.min(lastItem, Math.max(targetPosition, firstItem));
                return targetPosition;
            }
        };
        snapHelper.attachToRecyclerView(recyclerView);
        try {
            getData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void menulist() {
        String temp = "https://fodo1.herokuapp.com/hotel/menu/";
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, temp + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String jsonArray) {
//                            Log.v("responsefuck",jsonArray);
                        try {
                            array = new JSONArray(jsonArray);
                            HashMap<String, ArrayList<Item>> menu = new HashMap<>();
//        array = new JSONArray("[{\"_id\":\"5c3607018469fa00170ce1cf\",\"category\":\"Category 1\",\"food_name\":\"Food 1\",\"food_price\":\"100\",\"food_image\":\"https://images.pexels.com/photos/461198/pexels-photo-461198.jpeg\"},{\"_id\":\"5c3607018469fa00170ce1ce\",\"category\":\"Category 1\",\"food_name\":\"Food 2\",\"food_price\":\"100\",\"food_image\":\"https://images.pexels.com/photos/461198/pexels-photo-461198.jpeg\"},{\"_id\":\"5c3607018469fa00170ce1cd\",\"category\":\"Category 1\",\"food_name\":\"Food 3\",\"food_price\":\"100\",\"food_image\":\"https://images.pexels.com/photos/461198/pexels-photo-461198.jpeg\"},{\"_id\":\"5c3607018469fa00170ce1cc\",\"category\":\"Category 2\",\"food_name\":\"Food 3\",\"food_price\":\"100\",\"food_image\":\"https://images.pexels.com/photos/461198/pexels-photo-461198.jpeg\"},{\"_id\":\"5c3607018469fa00170ce1cb\",\"category\":\"Category 2\",\"food_name\":\"Food 2\",\"food_price\":\"100\",\"food_image\":\"https://images.pexels.com/photos/461198/pexels-photo-461198.jpeg\"},{\"_id\":\"5c3607018469fa00170ce1ca\",\"category\":\"Category 2\",\"food_name\":\"Food 1\",\"food_price\":\"100\",\"food_image\":\"https://images.pexels.com/photos/461198/pexels-photo-461198.jpeg\"},{\"_id\":\"5c3607018469fa00170ce1c9\",\"category\":\"Category 3\",\"food_name\":\"Food 1\",\"food_price\":\"100\",\"food_image\":\"https://images.pexels.com/photos/461198/pexels-photo-461198.jpeg\"},{\"_id\":\"5c3607018469fa00170ce1c8\",\"category\":\"Category 3\",\"food_name\":\"Food 2\",\"food_price\":\"100\",\"food_image\":\"https://images.pexels.com/photos/461198/pexels-photo-461198.jpeg\"},{\"_id\":\"5c3607018469fa00170ce1c7\",\"category\":\"Category 3\",\"food_name\":\"Food 3\",\"food_price\":\"100\",\"food_image\":\"https://images.pexels.com/photos/461198/pexels-photo-461198.jpeg\"}]");
                            for (int i = 0; i < array.length(); i++) {
                                Log.v("hello", String.valueOf(i));
                                JSONObject jsonObject = array.getJSONObject(i);
                                String category = jsonObject.getString("category");
//                                category += category;
                                if (menu.containsKey(category)) {
                                    menu.get(category).add(new Item(jsonObject.getString("food_name"),
                                            jsonObject.getString("food_image"),
                                            jsonObject.getString("food_price")));
                                } else {
                                    menu.put(category, new ArrayList<Item>());
                                    menu.get(category).add(new Item(jsonObject.getString("food_name"),
                                            jsonObject.getString("food_image"),
                                            jsonObject.getString("food_price")));
                                }
                            }
                            for (Map.Entry<String, ArrayList<Item>> entry : menu.entrySet()) {
                                String h = entry.getKey();
                                ArrayList<Item> m = entry.getValue();
                                menuList.add(new Menu(h, m));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        Log.v("responsefuck",mere);
                        menuAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
//                        Toast.makeText(MainActivity.this, "Unable to fetch data: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(request);
    }
    public void getData() throws JSONException {
        menulist();
    }
}
