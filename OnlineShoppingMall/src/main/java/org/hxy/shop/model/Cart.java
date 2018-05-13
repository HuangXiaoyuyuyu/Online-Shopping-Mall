package org.hxy.shop.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart implements Serializable{
    private Map<Integer,CartItem> map = new LinkedHashMap<>();
    private double total;
    public Collection<CartItem> getCartItems() {
        return map.values();
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    //添加到购物车
    public void addCartItem(CartItem cartItem) {
        //获得商品pid
        int pid = cartItem.getProduct().getPid();
        //判断购物车是否已经存在该购物项
        //若存在 数量增加
        //总计 = 总计 + 添加购物项小计
        if (map.containsKey(pid)) {
            CartItem cartItem1 = map.get(pid);
            cartItem1.setCount(cartItem1.getCount() + cartItem.getCount());
            //若不存在 向map中添加购物项
            //总计 = 总计 + 添加购物项小计
        } else {
            map.put(pid,cartItem);
        }
        total = total + cartItem.getSubtotal();
    }

    //删除购物项
    public void removeCartItem(int pid) {
        //将购物项移除购物车
        CartItem cartItem = map.remove(pid);
        //总计 = 总计 - 移除购物项小计
        total = total - cartItem.getSubtotal();
    }

    //清除购物车
    public void clearCart() {
        //购物项清空
        map.clear();
        //总计设为0
        total = 0;
    }
}
