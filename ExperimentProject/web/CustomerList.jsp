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
                <td><input type='text' name='name'></td>
            </tr>
            <tr>
                <td colspan='2' align='center'><input type='submit' value='查询'></td>
            </tr>
        </table>
    </form>
</div>
<%
    for (Cookie cookie:request.getCookies()) {
        if (cookie.getName().equals("last_login_time")) {
            out.print("最近一周登录时间：" + cookie.getValue());
        }
        if (cookie.getName().equals("login_times")) {
            out.print("您已登陆了：" + cookie.getValue()+"次");
        }
    }
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/neu_javaweb?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false","root", "qpalzm" );
    ResultSet rs = null;
    if(request.getAttribute("searchDB") != null && (boolean)request.getAttribute("searchDB")){
        rs =(ResultSet)request.getAttribute("result");
    }else{
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM customer_info";
        rs = stmt.executeQuery(sql);
    }
%>

<table id="customers" title="用户信息" class="easyui-datagrid" pagination="true" rownumbers="true" fitcolumns="true" singleselect="true" border="1">
    <thead>
    <tr>
        <th field ="1" width="50">客户ID</th>
        <th field ="2" width="100">客户姓名</th>
        <th field ="3" width="50">性别</th>
        <th field ="4" width="50">文化程度</th>
        <th field ="5" width="50">职业</th>
        <th field ="6" width="300">住址</th>
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
                <form method='post' action="customer.do">
                    <input hidden name ="mode" value="delete">
                <%
                    //删除button
                    out.print(" <button type=\"submit\" name=\"id\" value="+rs.getString("id")+">删除</button>");

                %>
                </form>
                <form method=post action="CustomerModify.jsp">
                    <%
                        //修改button
                        out.print(" <button type=\"submit\" name=\"id\" value="+rs.getString("id")+">修改</button>");

                    %>
                </form>
            </td>
        </tr>
    </tbody>
    <%
        }
    %>
</table>
<a href="CustomerAdd.jsp">
    <button>添加</button>
</a>

<a href="logout.do">
    <button>注销</button>
</a>

</body>
</html>
