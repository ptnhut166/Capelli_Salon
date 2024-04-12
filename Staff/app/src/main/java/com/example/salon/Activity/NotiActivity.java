package com.example.salon.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.salon.Booking.Booking_sel_locale;
import com.example.salon.Booking.user_class;
import com.example.salon.Helper.NavigationManager;
import com.example.salon.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class NotiActivity extends AppCompatActivity {
    ListView listnoti;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> notificationList;
    Button btndelete;
    Button btnchange;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications_page);
//        btnchange = findViewById(R.id.btn_change);
        btndelete = findViewById(R.id.btn_delete_infobk);
        listnoti = findViewById(R.id.notificationListView);
        notificationList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notificationList);
        listnoti.setAdapter(adapter);
        DatabaseReference addressRef = user_class.Database.getReference().child("userID").child(user_class.mAuth.getUid()).child("InfoBooking");
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(NotiActivity.this)
                        .setMessage("Are you sure you want to delete this schedule?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                addressRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {

                                            // Xóa nút "Address" hoặc thực hiện các thao tác cần thiết
                                            addressRef.removeValue();
                                            Toast.makeText(NotiActivity.this, "Deleted successfully.", Toast.LENGTH_SHORT).show();
                                        } else {

                                            Toast.makeText(NotiActivity.this, "You haven't booked a schedule yet. Schedule it and try again!", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        // Xử lý khi có lỗi xảy ra trong quá trình đọc dữ liệu
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
//        btnchange.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addressRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.exists()) {
//                            // Nút "Address" tồn tại và có dữ liệu
//                            // Xóa nút "Address" hoặc thực hiện các thao tác cần thiết
//                            addressRef.removeValue();
//                            Toast.makeText(NotiActivity.this, "Deleted successfully.", Toast.LENGTH_SHORT).show();
//                        } else {
//                            // Nút "Address" không tồn tại hoặc không có dữ liệu
//                            Toast.makeText(NotiActivity.this, "You haven't booked a schedule yet. Schedule it and try again!", Toast.LENGTH_SHORT).show();
//                        }
//
//                        // Khởi động Intent mới sau khi thực hiện các thao tác trên
//                        Intent newintent = new Intent(NotiActivity.this, Booking_sel_locale.class);
//                        startActivity(newintent);
//                        Toast.makeText(NotiActivity.this, "Successful registration", Toast.LENGTH_SHORT).show();
//                        finish();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                        // Xử lý khi có lỗi xảy ra trong quá trình đọc dữ liệu
//                    }
//                });
//            }
//        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userID = user.getUid();
            DatabaseReference userBookingRef = FirebaseDatabase.getInstance().getReference().child("userID").child(userID).child("InfoBooking");

            userBookingRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Log.d("Firebase", "DataSnapshot exists: " + dataSnapshot.getValue());
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Object data = snapshot.getValue();
                            if (data != null) {
                                String convertedData = data.toString();
                                notificationList.add(convertedData);
                            }


                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.d("Firebase", "No data found at this path");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("Firebase", "onCancelled called: " + databaseError.getMessage());
                }
            });

            ImageView backBtn =findViewById(R.id.backBtn);
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(NotiActivity.this, HomeActivity.class));

                }
            });
        }
    }
}