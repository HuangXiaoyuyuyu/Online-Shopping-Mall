package org.hxy.shop.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;
import org.hxy.shop.Utils.PageBean;
import org.hxy.shop.model.Order;
import org.hxy.shop.model.Orderitem;
import org.hxy.shop.model.User;
import org.hxy.shop.service.OrderService;

import java.util.List;

public class AdminOrderAction extends ActionSupport implements ModelDriven<Order> {
    private Order order = new Order();
    @Override
    public Order getModel() {
        return order;
    }

    //注入订单管理的Service
    private OrderService orderService;
    private Integer page;

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    //带分页的查询的方法
    public String findAllByPage() {
        PageBean<Order> pageBean = new PageBean<>();
        //设置当前页
        pageBean.setPage(page);
        //设置每页的记录
        int limit = 10;
        pageBean.setLimit(limit);
        //设置总记录
        int totalCount = 0;
        totalCount = orderService.findByCount();
        pageBean.setTotalCount(totalCount);
        //设置总页数
        int totalPage = 0;
        totalPage = (totalCount % limit == 0) ? totalCount/limit : totalCount/limit + 1;
        pageBean.setTotalPage(totalPage);
        //每页显示的数据集合
        int begin = (page - 1) * limit;
        List<Order> orders =  orderService.findByPage(begin,limit);
        pageBean.setList(orders);
        //将分页数据显示到页面上--值栈
        ActionContext.getContext().getValueStack().set("pageBean",pageBean);
        return "findAll";
    }

    //根据订单id查询订单项
    public String findOrderItem() {
        //根据订单id查询订单项
        List<Orderitem> list = orderService.findOrderItem(order.getOid());
        //通过值栈显示到页面
        ActionContext.getContext().getValueStack().set("list",list);
        return "findOrderItem";
    }

    //修改订单状态的方法
    public String updateState() {
        //根据订单id查询订单
        Order currOrder = orderService.findByOid(order.getOid());
        //修改订单状态
        currOrder.setState(3);
        orderService.update(currOrder);
        return "updateStateSuccess";
    }
}
