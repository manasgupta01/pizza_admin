package com.example.pizzaadmin;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    private static final String TAG = "OrderActivity";
    private FirebaseFirestore firestore;
    private RecyclerView ordersRecyclerView;
    private OrderAdapter orderAdapter;
    private List<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        firestore = FirebaseFirestore.getInstance();

        ordersRecyclerView = findViewById(R.id.orderRecyclerView);
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(orderList, this);
        ordersRecyclerView.setAdapter(orderAdapter);



        double totalPrice = orderAdapter.getTotalPrice();
        // Query Firestore for orders with "preparing" status
        CollectionReference ordersRef = firestore.collection("orders");
        Query query = ordersRef.whereEqualTo("status", "preparing").orderBy("orderId");

        query.addSnapshotListener((snapshot, error) -> {
            if (error != null) {
                Log.e(TAG, "Error listening for orders: ", error);
                return;
            }

            orderList.clear();

            for (DocumentSnapshot document : snapshot.getDocuments()) {
                Order order = document.toObject(Order.class);
                orderList.add(order);
            }

            orderAdapter.notifyDataSetChanged();
        });

        orderAdapter.setOnPreparedClickListener((position, order) -> {
            // Update order status to "prepared"
            DocumentReference orderRef = firestore.collection("orders").document(order.getOrderId());
            orderRef.update("status", "prepared")
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getApplicationContext(), "Order prepared", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Failed to mark order as prepared: " + e.getMessage());
                    });
        });
    }
}
