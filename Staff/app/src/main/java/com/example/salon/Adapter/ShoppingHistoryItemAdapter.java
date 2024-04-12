package com.example.salon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salon.Domain.Order;
import com.example.salon.R;

import java.util.List;

public class ShoppingHistoryItemAdapter extends RecyclerView.Adapter<ShoppingHistoryItemAdapter.ViewHolder> {


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView idTextView;
        public TextView nameTextView;
        public TextView phoneTextView;
        public TextView addressTextView;
        public TextView moneyTextView;
        public TextView detailTextView;
        public TextView emailTextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            idTextView = (TextView) itemView.findViewById(R.id.order_id);
            nameTextView = (TextView) itemView.findViewById(R.id.order_name);
            phoneTextView = (TextView) itemView.findViewById(R.id.order_phone);
            addressTextView = (TextView) itemView.findViewById(R.id.order_address);
            moneyTextView = (TextView) itemView.findViewById(R.id.order_money);
            detailTextView = (TextView) itemView.findViewById(R.id.order_detail);
            emailTextView = (TextView) itemView.findViewById(R.id.order_email);
        }
    }

    private List<Order> orders;

    public ShoppingHistoryItemAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);


        View contactView = inflater.inflate(R.layout.shopping_history_item, parent, false);


        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orders.get(position);

        TextView idTextView = holder.idTextView;
        idTextView.setText(order.getID());

        TextView nameTextView = holder.nameTextView;
        nameTextView.setText(order.getName());

        TextView addressTextView = holder.addressTextView;
        addressTextView.setText(order.getAddress());

        TextView phoneTextView = holder.phoneTextView;
        phoneTextView.setText(order.getPhone());

        TextView moneyTextView = holder.moneyTextView;
        moneyTextView.setText(order.getMoney());

        TextView detailTextView = holder.detailTextView;
        detailTextView.setText(order.getDetail());

        TextView emailTextView = holder.emailTextView;
        emailTextView.setText(order.getEmail());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }


}