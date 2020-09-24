package org.yanwen.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order implements Serializable {
    public Order(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "order_date")
    private Date order_date;

    @Column(name = "order_amount")
    private BigDecimal order_amount;

    @Column(name = "order_status")
    private String order_status;

    @Column(name = "order_shipping_date")
    private Date order_shipping_date;

    @Column(name = "order_tracking_number")
    private String order_tracking_number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "ordered_products",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")}
    )
    private Set<Product> products;

    public void addProduct(Product product){
        this.products.add(product);
        product.getOrders().add(this);
    }

    public void removeProduct(Product product){
        this.products.remove(product);
        product.getOrders().remove(this);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public BigDecimal getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(BigDecimal order_amount) {
        this.order_amount = order_amount;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public Date getOrder_shipping_date() {
        return order_shipping_date;
    }

    public void setOrder_shipping_date(Date order_shipping_date) {
        this.order_shipping_date = order_shipping_date;
    }

    public String getOrder_tracking_number() {
        return order_tracking_number;
    }

    public void setOrder_tracking_number(String order_tracking_number) {
        this.order_tracking_number = order_tracking_number;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                Objects.equals(order_date, order.order_date) &&
                Objects.equals(order_amount, order.order_amount) &&
                Objects.equals(order_status, order.order_status) &&
                Objects.equals(order_shipping_date, order.order_shipping_date) &&
                Objects.equals(order_tracking_number, order.order_tracking_number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order_date, order_amount, order_status, order_shipping_date, order_tracking_number);
    }
}
