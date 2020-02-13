<%--
  Created by IntelliJ IDEA.
  User: com.dbj
  Date: 2020/2/12
  Time: 18:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
  <head>
    <title>首页</title>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <link rel="stylesheet" type="text/css" href="static/css/style.css"/>
  </head>
  <body>
    <div class="workingroom">
      <div class="loginDiv">
        <c:if test="${empty subject.principal}">
          <a href="login.jsp">登录</a>
        </c:if>
        <c:if test="${!empty subject.principal}">
          <span class="desc">你好，${subject.principal}</span>
          <a href="doLogout">退出</a><br>
        </c:if>
        <a href="listProduct.jsp">查看产品</a><span class="desc">（登录后才可以查看）</span><br>
        <a href="deleteProduct.jsp">删除产品</a><span class="desc">（要有产品管理员的角色，zhang3没有,li4有）</span><br>
        <a href="deleteOrder.jsp">删除订单</a><span class="desc">（要有删除订单的权限，zhang3有，li4没有）</span>
      </div>
    </div>
  </body>
</html>
