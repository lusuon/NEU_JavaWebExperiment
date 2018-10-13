<%@ page import="java.sql.*" %><%--
  Created by IntelliJ IDEA.
  User: 54234
  Date: 2018-10-12
  Time: 17:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<div>
  <h1>客户列表</h1><br>
</div>
<div>
    <form method='post' action='customer.do'> /待修改
        <input hidden name="mode" value="search">
        <table bgcolor='#cccccc'>
            <tr>
                <td colspan='2'><h2>查询条件</h2></td>
            <tr>
                <td>用户ID：</td>
                <td><input type='text' name='id'></td>
            </tr>
            <tr>
                <td>用户姓名：</td>
                <td><input type='password' name='name'></td>
            </tr>
            <tr>
                <td colspan='2' align='center'><input type='submit' value='查询'></td>
            </tr>
        </table>
    </form>
</div>
//按钮未完工
<%
    Class.forName("com.mysql.jdbc.Driver");
    System.out.println("Connecting to database...");
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/neu_javaweb?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC","root", "qpalzm" );
    //Execute a query
    System.out.println("Creating statement...");
    Statement stmt = conn.createStatement();
    String sql = "SELECT * FROM customer_info";
    ResultSet rs = stmt.executeQuery(sql);
%>

<table id="customers" title="用户信息" class="easyui-datagrid" style="width:900px;height:400px;padding-left:200px;" pagination="true" rownumbers="true" fitcolumns="true" singleselect="true" border="1">
    <thead>
    <tr>
        <th field ="1" width="50">客户ID</th>
        <th field ="2" width="50">客户姓名</th>
        <th field ="3" width="50">性别</th>
        <th field ="4" width="50">文化程度</th>
        <th field ="5" width="50">职业</th>
        <th field ="6" width="50">住址</th>
        <th field ="7" width="50">操作</th>
    </tr>
    </thead>

    <tbody>
        <%
            while(rs.next()){
        %>
        <tr>
            <td>
                <%
                    out.print(rs.getString("id"));
                %>
            </td>
            <td>
                <%
                    out.print(rs.getString("name"));
                %>
            </td>
            <td>
                <%
                    out.print(rs.getString("gender"));
                %>
            </td>
            <td>
                <%
                    out.print(rs.getString("education"));
                %>
            </td>
            <td>
                <%
                    out.print(rs.getString("job"));
                %>
            </td>
            <td>
                <%
                    out.print(rs.getString("home"));
                %>
            </td>
            <td>
                
                <input hidden name="id" value= rs.getString("id")>
                <input hidden name="name" value= rs.getString("name")>
                <input hidden name="gender" value= rs.getString("gender")>
                <input hidden name="education" value= rs.getString("education")>
                <input hidden name="job" value= rs.getString("job")>
                <input hidden name="home" value= rs.getString("home")>
                <button type="button" name ="mode" value="delete">删除</button>
                <button type="submit" name ="mode" value="modify">修改（如何向修改页传参？-考虑httpSession）</button>
            </td>
        </tr>
    </tbody>
    <%
        }
    %>
</table>

</body>
</html>
