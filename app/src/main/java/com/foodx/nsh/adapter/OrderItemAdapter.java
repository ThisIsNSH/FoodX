package com.foodx.nsh.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.foodx.nsh.R;
import com.foodx.nsh.model.Cart;
import com.foodx.nsh.model.Hotel;
import com.foodx.nsh.model.OrderItem;

import org.w3c.dom.Text;

import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.MyViewHolder> {

    Activity context;
    List<OrderItem> orderItemList;

    public OrderItemAdapter(List<OrderItem> orderItemList, Activity context) {
        this.orderItemList = orderItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_order_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        OrderItem orderItem = orderItemList.get(position);
        holder.name.setText(orderItem.getName());
        holder.price.setText("Quantity: "+orderItem.getQuantity());

        System.out.println(orderItem.getHotel_id());
        System.out.println(orderItem.getOrder_id());

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(context.getString(R.string.base_url) + "/status/" + orderItem.getHotel_id() + "/" + orderItem.getOrder_id(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                holder.status.setText(response.replace("\"",""));
                switch (response.replace("\"","")){
                    case "Confirmed":
                        holder.status.setTextColor(context.getResources().getColor(R.color.color2));
                        break;
                    case "Cancelled":
                        holder.status.setTextColor(context.getResources().getColor(R.color.red));
                        break;
                    case "Out for Delivery":
                        break;
                    default:
                        break;

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }


    @Override
    public int getItemCount() {
        return orderItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, price, status;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            status = itemView.findViewById(R.id.f);
        }
    }


}