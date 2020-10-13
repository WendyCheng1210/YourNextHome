package org.yanwen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yanwen.model.Order;
import org.yanwen.repository.OrderDao;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    public Order save(Order order){return orderDao.save(order);}
    public List<Order> getOrders(){
        return orderDao.getOrders();
    }
    public Order update(Order order){
        return orderDao.update(order);
    }
    public Order getBy(long Id){
        return orderDao.getBy(Id);
    }
    public boolean delete(Order order){return orderDao.delete(order);}
}
