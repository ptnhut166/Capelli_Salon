package com.example.salon.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salon.Adapter.ShoppingHistoryItemAdapter;
import com.example.salon.Domain.Order;
import com.example.salon.Helper.NavigationManager;
import com.example.salon.Helper.OrderListCallback;
import com.example.salon.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends BaseActivity {
    private DatabaseReference mDatabase;

    LinearLayoutManager manager;
    BottomNavigationView bottomNavigationView;

    String uid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<Order> data = new ArrayList<>();
        setContentView(R.layout.shopping_history);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.orderlist);
        ShoppingHistoryItemAdapter adapter = new ShoppingHistoryItemAdapter(data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseUser current = FirebaseAuth.getInstance().getCurrentUser();
        uid = current.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        DatabaseReference userRef = mDatabase.child("Orders").child(uid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    Order order = ds.getValue(Order.class);
                    order.setID(ds.getKey());
                    data.add(order);

                }
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error, e.g., show an error message
                Toast.makeText(HistoryActivity.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
            }
        });

        bottomNavigationView = findViewById(R.id.bnv_his);
        bottomNavigationView.setSelectedItemId(R.id.action_his);
        BottomNavigationView bottomNav = findViewById(R.id.bnv_his);


        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.action_noti) {
                    // Xử lý khi click vào Notifications
                    NavigationManager.navigateToNotifications(HistoryActivity.this);
                    // Không gọi finish() ở đây nếu bạn không muốn kết thúc Activity hiện tại
                } else if (id == R.id.action_home) {
                    // Xử lý khi click vào Home
                    NavigationManager.navigateToHome(HistoryActivity.this);
                } else if (id == R.id.action_cart) {
                    // Xử lý khi click vào Cart
                    NavigationManager.navigateToCart(HistoryActivity.this);
                } else if (id == R.id.action_acc) {
                    // Xử lý khi click vào Cart
                    NavigationManager.navigateToProfile(HistoryActivity.this);
                }

                return true;
            }
        });
    }


}