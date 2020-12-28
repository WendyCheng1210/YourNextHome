package org.yanwen.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.yanwen.core.domain.Order;
import org.yanwen.core.service.OrderService;
import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderService orderService;

    //http://localhost:8080/orders
    @RequestMapping(value = "",method = RequestMethod.GET)
    public List<Order> getOrders(){
        List<Order> orders = orderService.getOrders();
        logger.debug("I am in the order controller");
        return orders;
    }
    //http://localhost:8080/orders/1
    @RequestMapping(value = "/{Id}", method = RequestMethod.GET)
    public Order getOrderById(@PathVariable(name = "Id")long id){
        logger.debug("I am in the team controllor get by " + id);
        return orderService.getBy(id);
    }

    //http://localhost:8080/orders?order_date = 2020-05-22
    @RequestMapping(value = "/{order_date}", method = RequestMethod.GET,params = {"order_date"})
    public Order getOrderByOrderDate(@RequestParam("order_date") String order_date){
        logger.info("pass in vairable order date: " + order_date);
        return null;
    }

    //http://localhost:8080/orders
    @RequestMapping(value = "",method = RequestMethod.POST)
    public void create(@RequestBody Order newObject, HttpRequest request){
        //TODO request.getHeaders().get("user_id");

        //TODO getUserbyId

        logger.debug(newObject.toString());
    }

}
