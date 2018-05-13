package org.hxy.shop.model;

public class CartItem {
    private Product product; //购物项商品信息
    private int count;       //购物项商品数量
    private double subtotal; //购物项小计

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getSubtotal() {
        return count * product.getShop_price();
    }
}
