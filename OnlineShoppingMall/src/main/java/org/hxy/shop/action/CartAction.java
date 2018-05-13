package org.hxy.shop.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.hxy.shop.model.Cart;
import org.hxy.shop.model.CartItem;
import org.hxy.shop.service.ProductService;

public class CartAction extends ActionSupport {
    private ProductService productService;
    private int pid;
    private int count;

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    //从session中获取购物车
    private Cart getCart() {
        Cart cart = (Cart) ServletActionContext.getRequest().getSession().getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            ServletActionContext.getRequest().getSession().setAttribute("cart",cart);
        }
        return cart;
    }

    //将购物项添加到购物车
    public String addCart(){
        CartItem cartItem = new CartItem();
        //接受pid  count
        cartItem.setCount(count);
        cartItem.setProduct(productService.findByPid(pid));
        Cart cart = getCart();
        cart.addCartItem(cartItem);
        return "addCart";
    }

    //清空购物车
    public String clearCart(){
        Cart cart = getCart();
        cart.clearCart();
        return "clearCart";
    }

    //从购物车移除购物项
    public String removeCart() {
        Cart cart = getCart();
        cart.removeCartItem(pid);
        return "removeCart";
    }

    //我的购物车
    public String myCart() {
        return "myCart";
    }
}
