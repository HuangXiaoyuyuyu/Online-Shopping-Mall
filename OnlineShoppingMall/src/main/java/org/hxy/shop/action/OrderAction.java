package org.hxy.shop.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;
import org.hxy.shop.Utils.PageBean;
import org.hxy.shop.Utils.PaymentUtil;
import org.hxy.shop.model.*;
import org.hxy.shop.service.OrderService;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class OrderAction extends ActionSupport implements ModelDriven<Order> {
    private Order order = new Order();
    private int page;
    private String pd_FrpId;
    private OrderService orderService;
    // 接收付款成功后的参数:
    private String r3_Amt;
    private String r6_Order;

    public void setR3_Amt(String r3_Amt) {
        this.r3_Amt = r3_Amt;
    }

    public void setR6_Order(String r6_Order) {
        this.r6_Order = r6_Order;
    }

    public Order getOrder() {
        return order;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public void setPd_FrpId(String pd_FrpId) {
        this.pd_FrpId = pd_FrpId;
    }

    @Override
    public Order getModel() {
        return order;
    }

    //生成订单
    public String save() {
        //订单数据的补全
            order.setOrdertime(new Date());
        //1:未付款 2:已付款未发货 3:已经发货，但未收货 4:交易完成
            order.setState(1);
        //总计数据是购物车中的信息
            Cart cart = (Cart) ServletActionContext.getRequest().getSession()
                    .getAttribute("cart");
            if (cart == null) {
                this.addActionError("亲！您还没有购物，请先去购物");
                return "msg";
            }
            order.setTotal(cart.getTotal());
        //设置订单中的订单项
            for(CartItem cartItem : cart.getCartItems()){
                Orderitem orderitem = new Orderitem();
                orderitem.setCount(cartItem.getCount());
                orderitem.setSubtotal(cartItem.getSubtotal());
                orderitem.setProduct(cartItem.getProduct());

                orderitem.setOrder(order);
                order.getOrderitems().add(orderitem);
            }
        //订单所属用户
            User existUser = (User) ServletActionContext.getRequest().getSession()
                    .getAttribute("existUser");
            if (existUser == null) {
                this.addActionError("亲，您还未登录，请先登录！");
                return "login";
            }
            order.setUser(existUser);
            orderService.save(order);
        //将订单的对象显示到页面上
        //通过值栈的方式显示：因为Order显示的对象就是模型驱动使用的对象
        //所以该对象就在栈顶
        //提交完订单后  需要清空购物车
        cart.clearCart();
            return "saveSuccess";
    }

    //我的订单查询
    public String findByUid(){
        PageBean<Order> pageBean = new PageBean<>();
        //设置当前页
        pageBean.setPage(page);
        //设置每页的记录
        int limit = 2;
        pageBean.setLimit(limit);
        //设置总记录
        User user = (User) ServletActionContext.getRequest().getSession()
                .getAttribute("existUser");
        int totalCount = 0;
        //根据用户的id进行查找
        totalCount = orderService.findByUid(user.getUid());
        //System.out.println(totalCount);
        pageBean.setTotalCount(totalCount);
        //设置总页数
        int totalPage = 0;
        totalPage = (totalCount % limit == 0) ? totalCount/limit : totalCount/limit + 1;
        pageBean.setTotalPage(totalPage);
        //每页显示的数据集合
        int begin = (page - 1) * limit;
        List<Order> orders =  orderService.findByPageUid(user.getUid(),begin,limit);
        pageBean.setList(orders);
        //将分页数据显示到页面上--值栈
        ActionContext.getContext().getValueStack().set("pageBean",pageBean);
        return "findByUid";
    }

    //根据订单id查询订单的方法
    public String findByOid() {
        order = orderService.findByOid(order.getOid());
        return "findByOid";
    }

    //订单付款
    public String payOrder() throws IOException {
        // 1.修改数据:
        System.out.println(order.getOid());
        Order currOrder = orderService.findByOid(order.getOid());
        currOrder.setAddr(order.getAddr());
        currOrder.setName(order.getName());
        currOrder.setPhone(order.getPhone());
        // 修改订单
        orderService.update(currOrder);
        // 2.完成付款:
        // 付款需要的参数:
        String p0_Cmd = "Buy"; // 业务类型:
        String p1_MerId = "10001126856";// 商户编号:
        String p2_Order = order.getOid()+"";// 订单编号:
        String p3_Amt = "0.01"; // 付款金额:
        String p4_Cur = "CNY"; // 交易币种:
        String p5_Pid = ""; // 商品名称:
        String p6_Pcat = ""; // 商品种类:
        String p7_Pdesc = ""; // 商品描述:
        String p8_Url = "http://localhost:8888/order_callBack.action"; // 商户接收支付成功数据的地址:
        String p9_SAF = ""; // 送货地址:
        String pa_MP = ""; // 商户扩展信息:
        String pd_FrpId = this.pd_FrpId;// 支付通道编码:
        String pr_NeedResponse = "1"; // 应答机制:
        String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl"; // 秘钥
        String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
                p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
                pd_FrpId, pr_NeedResponse, keyValue); // hmac
        // 向易宝发送请求:
        StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
        sb.append("p0_Cmd=").append(p0_Cmd).append("&");
        sb.append("p1_MerId=").append(p1_MerId).append("&");
        sb.append("p2_Order=").append(p2_Order).append("&");
        sb.append("p3_Amt=").append(p3_Amt).append("&");
        sb.append("p4_Cur=").append(p4_Cur).append("&");
        sb.append("p5_Pid=").append(p5_Pid).append("&");
        sb.append("p6_Pcat=").append(p6_Pcat).append("&");
        sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
        sb.append("p8_Url=").append(p8_Url).append("&");
        sb.append("p9_SAF=").append(p9_SAF).append("&");
        sb.append("pa_MP=").append(pa_MP).append("&");
        sb.append("pd_FrpId=").append(pd_FrpId).append("&");
        sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
        sb.append("hmac=").append(hmac);

        // 重定向:向易宝出发:
        ServletActionContext.getResponse().sendRedirect(sb.toString());
        return NONE;
    }

    // 付款成功后跳转回来的路径:
    public String callBack(){
        // 修改订单的状态:
        Order currOrder = orderService.findByOid(Integer.parseInt(r6_Order));
        // 修改订单状态为2:已经付款:
        currOrder.setState(2);
        orderService.update(currOrder);
        this.addActionMessage("支付成功!订单编号为: "+r6_Order +" 付款金额为: "+r3_Amt);
        return "msg";
    }

    //确认收货 修改订单状态
    public String updateState() {
        //根据订单id查询我的订单
        Order currOrder = orderService.findByOid(order.getOid());
        currOrder.setState(4);
        orderService.update(currOrder);
        return "updateStateSuccess";
    }
}
