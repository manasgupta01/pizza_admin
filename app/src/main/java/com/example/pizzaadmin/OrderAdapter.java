package com.example.pizzaadmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orders;
    private OrderItemClickListener clickListener;

    public OrderAdapter(List<Order> orders, OrderItemClickListener clickListener) {
        this.orders = orders;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_layout, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public interface OrderItemClickListener {
        void onOrderItemClick(int position);
    }

    class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView orderIdTextView;
        private TextView itemNameTextView;
        private Button statusButton;

        OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.text_order_id);
            itemNameTextView = itemView.findViewById(R.id.text_name);
            statusButton = itemView.findViewById(R.id.button_update_status);

            itemView.setOnClickListener(this);
            statusButton.setOnClickListener(this);
        }

        void bind(Order order) {
            orderIdTextView.setText("Order ID: " + order.getOrderId());
            itemNameTextView.setText("Item Name: " + order.getName());

            String status = order.getStatus();
            statusButton.setText(status);
            if (status.equals("preparing")) {
                statusButton.setEnabled(true);
            } else {
                statusButton.setEnabled(false);
            }
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                clickListener.onOrderItemClick(position);
            }
        }
    }
}
