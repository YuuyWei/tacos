package com.yuuy.tacos.repositories;

import com.yuuy.tacos.entities.Order;

public interface OrderRepository {

    Order save(Order order);
}
