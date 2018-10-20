<%--
  Created by IntelliJ IDEA.
  User: 54234
  Date: 2018-10-12
  Time: 17:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Hello</title>
  </head>
  <body>
    <%

    %>
    <h1>Hello world!</h1>
    <form action="login.do" method="post">
      <table>
        <td>管理员ID：</td>
        <td><input type='text' name='id'></td><br>
        <td>密码：</td>
        <td><input type='password' name='password'></td><br>
      </table>
      自动登录：<input type="checkbox" name="login" value="auto">
      <button type="submit">登录</button>
    </form>
    <a href='CustomerList.jsp'>会员列表</a>

  </body>
</html>
