<%--
  Created by IntelliJ IDEA.
  User: com.dbj
  Date: 2020/2/12
  Time: 19:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>

<!DOCTYPE html>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<div class="workingroom">

    <div class="errorInfo">${error}</div>
    <form action="login" method="post">
        账号： <input type="text" name="name"> <br>
        密码： <input type="password" name="password"> <br>
        <br>
        <input type="submit" value="登录">
        <br>
        <br>
        <div>
            <span class="desc">账号:zhang3 密码:12345 角色:admin</span><br>
            <span class="desc">账号:li4 密码:abcde 角色:productManager</span><br>
        </div>

    </form>
</div>