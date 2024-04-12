package com.example.salon.Booking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.salon.Helper.NavigationManager;
import com.example.salon.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class booking_sel_staff extends AppCompatActivity {
    RelativeLayout staff0, staff1, staff2, staff3;
    TextView namestaff0, namestaff1, namestaff2, namestaff3, phone0, phone1, phone2,phone3;
    user_class user_class;
    FirebaseUser user = user_class.mAuth.getCurrentUser();
    String userID = user.getUid();
    SharedPreferences prefs;

    private void setStaffOnClickListener(final RelativeLayout staff, final  TextView namestaffTextview, final  TextView pnumberTextview, final String staffKey) {
        staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ẩn RelativeLayout khi được nhấn
                staff.setVisibility(View.GONE);

                // Lưu trạng thái ẩn vào SharedPreferences
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(staffKey, true);
                editor.apply();

                String namestaff = namestaffTextview.getText().toString();
                String pnumber = pnumberTextview.getText().toString();
                user_class user_class = new user_class("", "","", "", namestaff, pnumber);

                Toast.makeText(getApplicationContext(), "Selected Name: " + namestaff + ", Phone number: " + pnumber, Toast.LENGTH_SHORT).show();

                FirebaseDatabase database = user_class.Database.getInstance();

                if (userID != null) {

                    DatabaseReference bookingRef = database.getReference().child("userID").child(userID).child("InfoBooking").child("Staff name");
                    bookingRef.setValue(namestaff);

                    bookingRef = database.getReference().child("userID").child(userID).child("InfoBooking").child("Phone number");
                    bookingRef.setValue(pnumber);
                }
                //intent
                Intent intent = new Intent(booking_sel_staff.this, booking_confirm.class);
                intent.putExtra("booking_info", user_class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_sel_staff);

        // Khởi tạo SharedPreferences
        prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);

        staff0 = findViewById(R.id.staff_0);
        staff1 = findViewById(R.id.staff_1);
        staff2 = findViewById(R.id.staff_2);
        staff3 = findViewById(R.id.staff_3);
        namestaff0 = findViewById(R.id.tv_staff_0);
        namestaff1 = findViewById(R.id.tv_staff_1);
        namestaff2 = findViewById(R.id.tv_staff_2);
        namestaff3 = findViewById(R.id.tv_staff_3);
        phone0 = findViewById(R.id.tv_pnumber_0);
        phone1 = findViewById(R.id.tv_pnumber_1);
        phone2 = findViewById(R.id.tv_pnumber_2);
        phone3 = findViewById(R.id.tv_pnumber_3);

        // Kiểm tra trạng thái ẩn từ SharedPreferences và áp dụng cho RelativeLayout
        boolean isStaff0Hidden = prefs.getBoolean("isStaff0Hidden", false);
        boolean isStaff1Hidden = prefs.getBoolean("isStaff1Hidden", false);
        boolean isStaff2Hidden = prefs.getBoolean("isStaff2Hidden", false);
        boolean isStaff3Hidden = prefs.getBoolean("isStaff3Hidden", false);

        if (isStaff0Hidden) {
            staff0.setVisibility(View.GONE);
        }
        if (isStaff1Hidden) {
            staff1.setVisibility(View.GONE);
        }
        if (isStaff2Hidden) {
            staff2.setVisibility(View.GONE);
        }
        if (isStaff3Hidden) {
            staff3.setVisibility(View.GONE);
        }

        setStaffOnClickListener(staff0, namestaff0, phone0, "isStaff0Hidden");
        setStaffOnClickListener(staff1, namestaff1, phone1, "isStaff1Hidden");
        setStaffOnClickListener(staff2, namestaff2, phone2, "isStaff2Hidden");
        setStaffOnClickListener(staff3, namestaff3, phone3, "isStaff3Hidden");

        Intent intent = getIntent();
        user_class user_class = (user_class) intent.getSerializableExtra("booking_info");
        // hoặc sử dụng getParcelableExtra nếu bạn đã sử dụng Parcelable
        if (user_class != null) {
            // Kiểm tra xem dữ liệu đã được chuyển qua hay chưa bằng cách in ra log
            Log.d("BookingInfo", "Name: " + user_class.getName());
            Log.d("BookingInfo", "Address: " + user_class.getAddress());
            Log.d("BookingInfo", "Time: " + user_class.getTime());
            // Kiểm tra các trường thông tin khác tương tự ở đây
        } else {
            Log.d("BookingInfo", "Null bookingInfo received");
        }
        ImageButton imageButton = (ImageButton) findViewById(R.id.back_staff);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(booking_sel_staff.this, Booking_sel_schedule.class);
                startActivity(intent);
            }
        });
    }
}
