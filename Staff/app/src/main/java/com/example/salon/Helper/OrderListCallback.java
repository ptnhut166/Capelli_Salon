package com.example.salon.Helper;

import com.example.salon.Domain.Order;

import java.util.List;

public interface OrderListCallback {
    void onCallback(List<Order> orders);
}
