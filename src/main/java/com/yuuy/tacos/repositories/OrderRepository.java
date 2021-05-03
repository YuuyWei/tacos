package com.yuuy.tacos.repositories;


import com.yuuy.tacos.entities.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
