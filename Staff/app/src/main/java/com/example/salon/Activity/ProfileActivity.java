package com.example.salon.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.salon.Domain.User;
import com.example.salon.Helper.NavigationManager;
import com.example.salon.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    public static FirebaseAuth mAuth;
    public User user;
    private TextView tvName, tvMo, tvAdd, tvDob,tvEm;
    private String uid;
    BottomNavigationView bottomNavigationView;

    public String exist;
    private DatabaseReference mDatabase;
    protected void onCreate(Bundle savedInstanceState) {



        FirebaseUser current = FirebaseAuth.getInstance().getCurrentUser();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        bottomNavigationView  = findViewById(R.id.bnv_profile);
        bottomNavigationView.setSelectedItemId(R.id.action_acc);
        Button btn_Signout= findViewById(R.id.btn_logout);
        Button btn_Update =findViewById(R.id.btn_update);
        Button btn_Report =findViewById(R.id.btn_report);

        BottomNavigationView bottomNav = findViewById(R.id.bnv_profile);

        tvName = findViewById(R.id.tv_name);
        tvMo = findViewById(R.id.tv_mobile);
        tvEm =findViewById(R.id.tv_email);
        tvAdd = findViewById(R.id.tv_address);
        tvDob = findViewById(R.id.tv_dob);



        uid= current.getUid();

        TextView idTxt = findViewById(R.id.tv_id);

        idTxt.setText(current.getUid());

        mDatabase = FirebaseDatabase.getInstance().getReference();
        getUserData(uid);



        btn_Signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ProfileActivity.this)
                        .setMessage("Are you sure you want to sign out?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, Profile_edit.class);

                intent.putExtra("check", exist);

                startActivity(intent);
                finish();
            }
        });

        btn_Report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0347712598"));
                startActivity(intent);
            }
        });
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.action_noti) {
                    // Xử lý khi click vào Notifications
                    NavigationManager.navigateToNotifications(ProfileActivity.this);
                    // Không gọi finish() ở đây nếu bạn không muốn kết thúc Activity hiện tại
                } else if (id == R.id.action_home) {
                    // Xử lý khi click vào Home
                    NavigationManager.navigateToHome(ProfileActivity.this);
                } else if (id == R.id.action_cart) {
                    // Xử lý khi click vào Cart
                    NavigationManager.navigateToCart(ProfileActivity.this);
                }
                else if (id == R.id.action_his) {
                    // Xử lý khi click vào Cart
                    NavigationManager.navigateToHistory(ProfileActivity.this);
                }


                return true;
            }
        });
    }


    private void getUserData(String userID) {
        DatabaseReference userRef = mDatabase.child("userID").child(userID).child("Personal Information");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {


                    String address_value = dataSnapshot.child("address").getValue(String.class);
                    String birthday_value = dataSnapshot.child("dob").getValue(String.class);
                    String emai_value = dataSnapshot.child("email").getValue(String.class);
                    String name_value = dataSnapshot.child("name").getValue(String.class);
                    String phone_value = dataSnapshot.child("mobile").getValue(String.class);

                    tvName.setText(String.valueOf(name_value));
                    tvMo.setText(String.valueOf(phone_value));
                    tvAdd.setText(String.valueOf(address_value));
                    tvDob.setText(String.valueOf(birthday_value));
                    tvEm.setText(String.valueOf(emai_value));

                    exist = "Yes";
                } else {
                    exist = "No";
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error, e.g., show an error message
                Toast.makeText(ProfileActivity.this, "Failed to retrieve user data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
