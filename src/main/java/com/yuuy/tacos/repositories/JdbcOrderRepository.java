package com.yuuy.tacos.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuuy.tacos.entities.Order;
import com.yuuy.tacos.entities.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert tacoOrderInserter;
    private ObjectMapper objectMapper;

    @Autowired
    public JdbcOrderRepository(JdbcTemplate jdbc) {
        this.orderInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order")
                .usingGeneratedKeyColumns("id");

        this.tacoOrderInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order_Tacos");

        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Order save(Order order) {
        order.setPlacedTime(new Date());

        long orderId = saveOrderDetails(order);
        order.setId(orderId);

        List<Taco> tacoList = order.getTacos();
        for (Taco taco :
                tacoList) {
            saveTacoToOrder(taco, orderId);
        }

        return order;
    }

    private void saveTacoToOrder(Taco taco, long orderId) {
        Map<String, Object> values = new HashMap<>();
        values.put("tacoOrder", orderId);
        values.put("taco", taco.getId());

        tacoOrderInserter.execute(values);
    }

    private long saveOrderDetails(Order order) {
        @SuppressWarnings("unchecked")
        Map<String, Object> values = objectMapper.convertValue(order, Map.class);
        values.put("placedTime", order.getPlacedTime());

        long orderId = orderInserter.executeAndReturnKey(values).longValue();

        return orderId;
    }
}
