package com.example.pizzaadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Order> orderList;
    private Context context;
    private OnPreparedClickListener onPreparedClickListener;
    public double getTotalPrice() {
        double totalPrice = 0.0;
        for (Order order : orderList) {
            totalPrice += order.getTotalAmount();
        }
        return totalPrice;
    }
    public OrderAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    public void setOnPreparedClickListener(OnPreparedClickListener listener) {
        this.onPreparedClickListener = listener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);

        holder.orderIdTextView.setText("Order ID: " + order.getOrderId());
        holder.orderTotalTextView.setText("Total Price: $" + order.getTotalPrice());

        holder.prepareButton.setOnClickListener(v -> {
            if (onPreparedClickListener != null) {
                onPreparedClickListener.onPreparedClick(position, order);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public interface OnPreparedClickListener {
        void onPreparedClick(int position, Order order);
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdTextView;
        TextView orderTotalTextView;
        Button prepareButton;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.orderIdTextView);
            orderTotalTextView = itemView.findViewById(R.id.orderTotalTextView);
            prepareButton = itemView.findViewById(R.id.prepareButton);
        }
    }
}

