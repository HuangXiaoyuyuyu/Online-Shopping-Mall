<%--
  Created by IntelliJ IDEA.
  User: 25375
  Date: 2018/5/1
  Time: 0:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<table border="1" cellspacing="0" cellpadding="0" width="100%">
    <tr>
        <td align="center">商品图片</td>
        <td align="center">商城价</td>
        <td align="center">数量</td>
        <td align="center">小计</td>
    </tr>
    <s:iterator var="orderItem" value="list">
        <tr>
            <td align="center"><img width="45" height="45" src="<s:property value="#orderItem.product.image" />"></td>
            <td align="center"><s:property value="#orderItem.product.shop_price" /></td>
            <td align="center"><s:property value="#orderItem.count" /></td>
            <td align="center"><s:property value="#orderItem.subtotal"/></td>
        </tr>
    </s:iterator>
</table>