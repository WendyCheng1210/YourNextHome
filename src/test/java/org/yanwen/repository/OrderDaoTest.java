package org.yanwen.repository;
import org.hibernate.HibernateException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.yanwen.ApplicationBootstrap;
import org.yanwen.model.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBootstrap.class)
public class OrderDaoTest {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CategoryDao categoryDao;

    private Order o1;
    private Brand b1;
    private Brand b2;
    private Category c1;
    private Category c2;
    private Product p1;
    private Product p2;

    @Before
    public void setUp(){
        o1 = new Order();
        o1.setUser(new User(1));
        o1.setOrder_date(new Timestamp(new Date(2020-01-01).getTime()));
        o1.setOrder_amount(new BigDecimal(90.00));
        o1.setOrder_status("Shipped");
        o1.setOrder_shipping_date(new Timestamp(new Date(2020-01-02).getTime()));
        o1.setOrder_tracking_number("1116089956379289991");

        b1 = new Brand();
        b1.setId(2);
        b1.setName("Roundhill");

        c1 = new Category();
        c1.setId(3);
        c1.setCategory_name("Furniture");
        c1.setDescription("All about comfort!");
        //c1 = categoryDao.getBy(3);

        p1 = new Product();
        p1.setName("Queen Anna Style Floor Cheval Mirror");
        p1.setBrand(b1);
        p1.setCategory(c1);
        p1.setPrice(new BigDecimal(50));
        o1.addProduct(p1);


        b2 = new Brand();
        b2.setId(1);
        b2.setName("MyPillow");

        c2 = new Category();
        c2.setId(1);
        c2.setCategory_name("Bedding");
        c2.setDescription("Turn your bedroom into your sweet dream place!");
        //c2 = categoryDao.getBy(1);

        p2 = new Product();
        p2.setName(" Classic Medium Fill Bed Pillow");
        p2.setBrand(b2);
        p2.setCategory(c2);
        p2.setPrice(new BigDecimal(40));
        o1.addProduct(p2);

//        p1 = productDao.save(p1);
//        p2 = productDao.save(p2);
        o1 = orderDao.save(o1);

    }

    @After
    public void tearDown(){
        productDao.delete(p1);
        productDao.delete(p2);
        orderDao.delete(o1);
    }

    @Test
    public void getOrdersTest(){
        List<Order> orders = orderDao.getOrders();
        int expectedNumOfOrder = 4;
        Assert.assertEquals(expectedNumOfOrder, orders.size());

        List<Product> products = productDao.getProducts();
        int expectedNumOfProduct = 3;
        Assert.assertEquals(expectedNumOfProduct, products.size());
    }

    @Test
    public void getOrderEagerByTest(){
        Order order = orderDao.getOrderEagerBy(o1.getId());
        assertNotNull(order);
        assertEquals(order.getOrder_date(),o1.getOrder_date());
        Set<Product> products = order.getProducts();
        assertTrue(products.size() > 0);
    }

    @Test
    public void getOrderByTest(){
        Order order = orderDao.getBy(o1.getId());
        assertNotNull(order);
        assertEquals(order.getOrder_date(),o1.getOrder_date());
        System.out.println(order.getProducts());
    }

}
