package com.mangwalo.nsh.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mangwalo.nsh.R;
import com.mangwalo.nsh.model.Cart;
import com.mangwalo.nsh.model.Item;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThisIsNSH on 1/5/2019.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    List<Item> itemList;
    Activity context;
    int max=10;
    String hotelid="";
    SharedPreferences sharedPreferences,ordered;
    SharedPreferences.Editor editor;
    Gson gson = new Gson();
    String key = "Key";
    int color;
    List<Cart> myOrders = new ArrayList<>();
    public ItemAdapter(List<Item> itemList, Activity context,String hotelid,int color) {
        this.color = color;
        this.itemList = itemList;
        this.context = context;
        this.hotelid = hotelid;
//        setHasStableIds(true);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        sharedPreferences = context.getSharedPreferences("Myprefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String response=sharedPreferences.getString(key , "null");
        if (!response.equals("null"))
            myOrders = gson.fromJson(response,new TypeToken<List<Cart>>(){}.getType());
        final Item item = itemList.get(position);
        holder.name.setText(item.getName());
        holder.price.setText("Price: "+item.getPrice());
//        Picasso.get().load(item.getImage()).into(holder.image);
        Picasso.get().load("https://i.dlpng.com/static/png/151888_preview.png").into(holder.image1);
        holder.button.setBackground(context.getResources().getDrawable(drawable(color)));
        holder.button1.setBackground(context.getResources().getDrawable(drawable(color)));
        holder.button2.setBackground(context.getResources().getDrawable(drawable(color)));
        holder.button2.setText("ADD TO CART");
        if(item.getName().equals("Roti")) {
            max = 50;
        }
        holder.button.setVisibility(View.GONE);
        holder.button1.setVisibility(View.GONE);
        holder.quantity.setVisibility(View.GONE);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(holder.button, "scaleX", 0.95f);
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(holder.button, "scaleX", 1f);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(holder.button, "scaleY", 0.95f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(holder.button, "scaleY", 1f);

                objectAnimator.setDuration(200);
                objectAnimator1.setDuration(100);
                objectAnimator2.setDuration(200);
                objectAnimator3.setDuration(100);

                objectAnimator1.setStartDelay(200);
                objectAnimator3.setStartDelay(200);

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(objectAnimator,objectAnimator1,objectAnimator2,objectAnimator3);
                animatorSet.start();
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if(holder.quantity.getText().toString().equals(String.valueOf(max)))
                        {
                            holder.button.setEnabled(false);
                            holder.button1.setEnabled(true);
                        }
                        else{
                            int quantity = Integer.parseInt(holder.quantity.getText().toString());
                            holder.quantity.setText(String.valueOf(quantity+1));
                            holder.button1.setEnabled(true);
                        }
                        if(!holder.quantity.getText().toString().equals("0")){
                            holder.button2.setVisibility(View.VISIBLE);
                            holder.button2.setText("DONE");
                        }
                        else {
                            holder.button.setVisibility(View.GONE);
                            holder.button1.setVisibility(View.GONE);
                            holder.quantity.setVisibility(View.GONE);
                            holder.button2.setText("ADD TO CART");
                        }
                    }
                });
            }
        });

        holder.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(holder.button1, "scaleX", 0.95f);
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(holder.button1, "scaleX", 1f);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(holder.button1, "scaleY", 0.95f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(holder.button1, "scaleY", 1f);

                objectAnimator.setDuration(200);
                objectAnimator1.setDuration(100);
                objectAnimator2.setDuration(200);
                objectAnimator3.setDuration(100);

                objectAnimator1.setStartDelay(200);
                objectAnimator3.setStartDelay(200);

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(objectAnimator,objectAnimator1,objectAnimator2,objectAnimator3);
                animatorSet.start();
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if(holder.quantity.getText().toString().equals("0")){
                            holder.button.setEnabled(true);
                            holder.button1.setEnabled(false);
                        }
                        else{
                            int quantity = Integer.parseInt(holder.quantity.getText().toString());
                            holder.quantity.setText(String.valueOf(quantity-1));
                            holder.button.setEnabled(true);

                        }
                        if(!holder.quantity.getText().toString().equals("0")){
                            holder.button2.setVisibility(View.VISIBLE);
                            holder.button2.setText("DONE");
                        }
                        else {
                            holder.button.setVisibility(View.GONE);
                            holder.button1.setVisibility(View.GONE);
                            holder.quantity.setVisibility(View.GONE);
                            holder.button2.setText("ADD TO CART");
                        }
                    }
                });
            }
        });

        holder.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(holder.button2, "scaleX", 0.95f);
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(holder.button2, "scaleX", 1f);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(holder.button2, "scaleY", 0.95f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(holder.button2, "scaleY", 1f);

                objectAnimator.setDuration(200);
                objectAnimator1.setDuration(100);
                objectAnimator2.setDuration(200);
                objectAnimator3.setDuration(100);

                objectAnimator1.setStartDelay(200);
                objectAnimator3.setStartDelay(200);

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(objectAnimator,objectAnimator1,objectAnimator2,objectAnimator3);
                animatorSet.start();
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if (holder.button2.getText() == "DONE") {
                            int notExists = 1;
                            Cart cart = new Cart(item.getName(), holder.quantity.getText().toString(), hotelid ,item.getPrice());
//                            Log.v("SIZING", String.valueOf(myOrders.size()));
//                            Log.v("fuckoff",item.getPrice());
                            myOrders.add(cart);

                            String json = gson.toJson(myOrders);
                            Log.v("menulist",json);
                            editor.putString(key, json);
                            editor.apply();
                            Toast.makeText(context, "Your item has been added to the cart.", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            holder.button.setVisibility(View.VISIBLE);
                            holder.button1.setVisibility(View.VISIBLE);
                            holder.quantity.setVisibility(View.VISIBLE);
                            holder.quantity.setText("1");
                            holder.button2.setText("DONE");
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,price,quantity;
        ImageView image,image1;
        ImageView button,button1;
        Button button2;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            image1 = itemView.findViewById(R.id.image1);
            image = itemView.findViewById(R.id.image);
            price = itemView.findViewById(R.id.price);
            button = itemView.findViewById(R.id.addition);
            button1 = itemView.findViewById(R.id.subtraction);
            button2 = itemView.findViewById(R.id.addtocart);
            quantity = itemView.findViewById(R.id.quantity);
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public int drawable(int color){
        switch (color){
            case R.color.color0 :
                return R.drawable.curvedback;
            case R.color.color1 :
                return R.drawable.curvedback1;
            case R.color.color2 :
                return R.drawable.curvedback2;
            case R.color.color3 :
                return R.drawable.curvedback3;
            case R.color.color4 :
                return R.drawable.curvedback4;
            case R.color.color5 :
                return R.drawable.curvedback5;
            case R.color.color6 :
                return R.drawable.curvedback6;
        }
        return R.drawable.curvedback6;
    }
}
