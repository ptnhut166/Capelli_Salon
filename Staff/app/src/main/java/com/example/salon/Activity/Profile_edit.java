package com.example.salon.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.salon.Domain.User;
import com.example.salon.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Profile_edit extends AppCompatActivity {
    private DatabaseReference mDatabase;

    private String uid;
    TextInputEditText ed_name, ed_phone,ed_mail, ed_birthday, ed_address;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit);

        FirebaseUser current = FirebaseAuth.getInstance().getCurrentUser();

        uid = current.getUid();
        ed_name=findViewById(R.id.edt_name);
        ed_birthday=findViewById(R.id.edt_dob);
        ed_address=findViewById(R.id.edt_address);
        ed_mail=findViewById(R.id.edt_email);
        ed_phone=findViewById(R.id.edt_mobile);


        Button btn_cancel = findViewById(R.id.btn_cancel);
        Button btn_save =findViewById(R.id.btn_save);

        Intent intent = getIntent();

        String check = intent.getStringExtra("check");

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile_edit.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name =ed_name.getText().toString();
                String number =ed_phone.getText().toString();
                String email =ed_mail.getText().toString();
                String address =ed_address.getText().toString();
                String dob =ed_birthday.getText().toString();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(number) && !TextUtils.isEmpty(email)&& !TextUtils.isEmpty(address) && !TextUtils.isEmpty(dob)) {
                    mDatabase = FirebaseDatabase.getInstance().getReference();

                    if (check =="Yes"){
                        updateInfo(name, number, email, address, dob);
                    }
                    else {
                        writeNewInfo(name, number, email, address, dob);
                    }

                    Intent intent = new Intent(Profile_edit.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();


                } else {
                    Toast.makeText(Profile_edit.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    public void writeNewInfo(String name, String mobile,String email, String address, String dob) {
        // Check if userID is not null before proceeding

            User user = new User(name, mobile, email, address, dob);
            DatabaseReference mRef=mDatabase.child("userID").child(uid).child("Personal Information");
            mRef.setValue(user);
            Toast.makeText(Profile_edit.this, "Write new information success", Toast.LENGTH_SHORT).show();

    }
    public void updateInfo(String name, String mobile,String email, String address, String dob) {
        // Create a map to hold the updated data
        Map<String, Object> updates = new HashMap<>();
        updates.put("name", name);
        updates.put("mobile", mobile);
        updates.put("email", email);
        updates.put("address", address);
        updates.put("dob", dob);


        // Update the user data in the "users" node with the specified userID
        mDatabase.child("users").child(uid).child("Personal Information").updateChildren(updates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Handle the success, e.g., show a toast
                        Toast.makeText(Profile_edit.this, "User updated successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the failure, e.g., show an error message
                        Toast.makeText(Profile_edit.this, "Failed to update user", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
