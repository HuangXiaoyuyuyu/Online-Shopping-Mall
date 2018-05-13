<%@ page import="com.opensymphony.xwork2.ActionContext" %>
<%@ page import="org.hxy.shop.Utils.PageBean" %>
<%@ page import="org.hxy.shop.model.Product" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0048)http://localhost:8080/mango/product/list/1.jhtml -->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>宇神商城</title>
    <link href="./css/common.css" rel="stylesheet" type="text/css"/>
    <link href="./css/product.css" rel="stylesheet" type="text/css"/>

</head>
<body>
<div class="container header">
    <div class="span5">
        <div class="logo">
            <a href="http://localhost:8080/mango/">
                <img src="./image/r___________renleipic_01/logo.jpg" alt="宇神商城">
            </a>
        </div>
    </div>
    <div class="span9">
        <div class="headerAd">
            <img src="./image/header.jpg" width="320" height="50" alt="正品保障" title="正品保障">
        </div>	</div>
    <%@ include file="menu.jsp"%>
</div>
<div class="container productList">
    <div class="span6">
        <div class="hotProductCategory">
            <s:iterator var="c" value="#session.categories">
            <dl>
                <dt>
                    <a href="product_findByCid.action?cid=<s:property value="#c.cid"/>&page=1"><s:property value="#c.cname"/></a>
                </dt>
                <s:iterator var="cs" value="#c.categorySeconds">
                    <dd>
                        <a href="product_findByCsid.action?csid=<s:property value="#cs.csid"/>&page=1"><s:property value="#cs.csname"/></a>
                    </dd>
                </s:iterator>
            </dl>
            </s:iterator>
        </div>
    </div>
    <div class="span18 last">

        <form id="productForm" action="./image/蔬菜 - Powered By Mango Team.htm" method="get">
            <div id="result" class="result table clearfix">
                <ul>
                    <s:iterator var="p" value="pageBean.list">
                        <li>
                            <a href="product_findByPid.action?pid=<s:property value="#p.pid" />">
                                <img src="<s:property value="#p.image" />" width="170" height="170"  style="display: inline-block;">

                                <span style='color:green'>
                                    <s:property value="#p.pname"/>
                                </span>

                                <span class="price">
                                    商城价： ￥<s:property value="#p.shop_price" />
                                </span>
                            </a>
                        </li>
                    </s:iterator>
                </ul>
            </div>
            <div class="pagination">
                <span>第<s:property value="page" />/<s:property value="pageBean.totalPage" />页</span>
                <s:if test="csid != null">
                    <s:if test="pageBean.page != 1">
                        <a href="product_findByCsid.action?csid=<s:property value="csid" />&page=1" class="firstPage">&nbsp;</a>
                        <a href="product_findByCsid.action?csid=<s:property value="csid" />&page=<s:property value="pageBean.page-1" />" class="previousPage">&nbsp;</a>
                    </s:if>
                    <%--<span class="currentPage">1</span>--%>

                    <%
                        PageBean<Product> pageBean = (PageBean<Product>) ActionContext.getContext().getValueStack().findValue("pageBean");
                        for (int i=1;i<=pageBean.getTotalPage() ;i++) {
                    %>
                    <a href="product_findByCsid.action?csid=<s:property value="csid" />&page=<%=i%>"><%=i%></a>
                    <%
                        }
                    %>

                    <s:if test="pageBean.page != pageBean.totalPage">
                        <a class="nextPage" href="product_findByCsid.action?csid=<s:property value="csid" />&page=<s:property value="pageBean.page + 1"/>">&nbsp;</a>
                        <a class="lastPage" href="product_findByCsid.action?csid=<s:property value="csid" />&page=<s:property value="pageBean.totalPage"/>">&nbsp;</a>
                    </s:if>
                </s:if>
            </div>
        </form>
    </div>
</div>
<div class="container footer">
    <div class="span24">
        <div class="footerAd">
            <img src="./image/footer.jpg" width="950" height="52" alt="我们的优势" title="我们的优势">
        </div>	</div>
    <div class="span24">
        <ul class="bottomNav">
            <li>
                <a >关于我们</a>
                |
            </li>
            <li>
                <a>联系我们</a>
                |
            </li>
            <li>
                <a >诚聘英才</a>
                |
            </li>
            <li>
                <a >法律声明</a>
                |
            </li>
            <li>
                <a>友情链接</a>
                |
            </li>
            <li>
                <a target="_blank">支付方式</a>
                |
            </li>
            <li>
                <a  target="_blank">配送方式</a>
                |
            </li>
            <li>
                <a >官网</a>
                |
            </li>
            <li>
                <a >论坛</a>

            </li>
        </ul>
    </div>
    <div class="span24">
        <div class="copyright">Copyright © 2018-2099 网上商城 版权所有</div>
    </div>
</div>
<s:debug></s:debug>
</body></html>
