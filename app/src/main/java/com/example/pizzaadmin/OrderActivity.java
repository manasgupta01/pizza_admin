package com.example.pizzaadmin;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    private static final String TAG = "OrderActivity";
    private FirebaseFirestore firestore;
    String oid;
    private List<Order> ordersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        firestore = FirebaseFirestore.getInstance();
        fetchPreparingOrders();
    }

    private void fetchPreparingOrders() {
        CollectionReference ordersRef = firestore.collection("orders");
        Query query = ordersRef.whereEqualTo("status", "preparing");

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    oid = document.getId();
                    String orderId = document.getString("orderId");
                    String itemId = document.getString("itemId");
                    String name = document.getString("name");
                    double price = document.getDouble("price");


//
//        imageString = Base64.encodeToString(order.getImageBytes(), Base64.DEFAULT);

                    String images = document.getString("imageBytes");
                    byte[] imageBytes = Base64.decode(images, Base64.DEFAULT);

                    String status = document.getString("status");
                    Order order = new Order(orderId, itemId, name, price,imageBytes, status);
                    ordersList.add(order);
                }

                setupOrdersRecyclerView();
            } else {
                Log.e(TAG, "Error fetching orders: " + task.getException());
            }
        });
    }

    private void setupOrdersRecyclerView() {
        RecyclerView ordersRecyclerView = findViewById(R.id.ordersRecyclerView);
        OrdersAdapter ordersAdapter = new OrdersAdapter(ordersList);
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ordersRecyclerView.setAdapter(ordersAdapter);
    }

    private void updateOrderStatus(Order order) {
        String orderId = order.getOrderId();

        DocumentReference orderRef = firestore.collection("orders").document(oid);

        orderRef.update("status", order.getStatus())
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Status updated successfully: " + order.getStatus());
                    Toast.makeText(OrderActivity.this, "Status updated successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to update status: " + e.getMessage());
                    Toast.makeText(OrderActivity.this, "Failed to update status", Toast.LENGTH_SHORT).show();
                });
    }

    public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {
        private List<Order> ordersList;

        public OrdersAdapter(List<Order> ordersList) {
            this.ordersList = ordersList;
        }

        public class OrderViewHolder extends RecyclerView.ViewHolder {
            public TextView textOrderID;
            public TextView textName;
            public Button buttonChangeStatus;

            public OrderViewHolder(View itemView) {
                super(itemView);
                textOrderID = itemView.findViewById(R.id.textOrderID);
                textName = itemView.findViewById(R.id.textName);
                buttonChangeStatus = itemView.findViewById(R.id.buttonChangeStatus);
            }
        }

        @NonNull
        @Override
        public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
            return new OrderViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
            Order order = ordersList.get(position);
            holder.textOrderID.setText("Order ID: " + order.getOrderId());
            holder.textName.setText("Name: " + order.getName());

            holder.buttonChangeStatus.setOnClickListener(v -> {
                order.setStatus("prepared");
                updateOrderStatus(order);
                notifyDataSetChanged();
            });
        }

        @Override
        public int getItemCount() {
            return ordersList.size();
        }
    }
}
