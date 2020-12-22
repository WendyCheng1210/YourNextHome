package org.yanwen.core.repository;

import org.yanwen.core.domain.Order;

import java.util.List;

public interface OrderDao {
    Order save(Order order);
    List<Order> getOrders();
    Order getBy(long id);
    Order getOrderEagerBy(long id);
    Order update(Order order);
    boolean delete(Order order);
}
