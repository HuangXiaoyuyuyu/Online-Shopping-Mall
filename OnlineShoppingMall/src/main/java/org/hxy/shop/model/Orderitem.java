package org.hxy.shop.model;

import javax.persistence.*;

@Entity
public class Orderitem {
    private int itemid;
    private int count;
    private double subtotal;
    private Product product;
    private Order order;
    @Id
    @GeneratedValue
    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    @ManyToOne
    @JoinColumn(name = "pid")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "oid")
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
