package com.example.salon.Domain;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class User implements Serializable {

    private String email;
    private String name;
    private String mobile;
    private String address;
    private String dob;
    public static FirebaseAuth mAuth;
    public static FirebaseDatabase Database = FirebaseDatabase.getInstance();


    public User()
    {

    }
    public User(  String name, String mobile,String email, String address, String dob){

        this.name=name;
        this.mobile=mobile;
        this.email=email;
        this.address=address;
        this.dob=dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public String getMobile() {
        return mobile;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
