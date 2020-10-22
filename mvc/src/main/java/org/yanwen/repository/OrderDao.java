package org.yanwen.repository;

import org.yanwen.model.Order;

import java.util.List;

public interface OrderDao {
    Order save(Order order);
    List<Order> getOrders();
    Order getBy(long id);
    Order getOrderEagerBy(long id);
    Order update(Order order);
    boolean delete(Order order);
}
