<%@ page import="java.sql.*" %><%--
  Created by IntelliJ IDEA.
  User: 54234
  Date: 2018-10-12
  Time: 17:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>客户列表</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    用户管理系统 <small>用户列表</small>
                </h1>
            </div>
            <div class="jumbotron well">
                <h4>
                    <div name="Use of cookies" style="text-align:center;vertical-align: center;margin:auto">
                        <%
                            out.print("您好，" + request.getParameter("id"));
                            for (Cookie cookie:request.getCookies()) {
                                if (cookie.getName().equals("last_login_time")) {
                                    out.print("最近一周登录时间：" + cookie.getValue());
                                }
                        %>
                        <br>
                        <%
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
                    </div>
                </h4>

                <div style="text-align:center;vertical-align: center;margin:auto">
                    <a class="btn btn-primary btn-large" href="CustomerAdd.jsp" >
                        添加
                    </a>

                    <a class="btn btn-primary btn-large" href="logout.do" style="text-align:center;vertical-align: center;margin:auto">
                        注销
                    </a>
                </div>
            </div>
        </div>
    </div>


<div>
    <form method='post' action='customer.do'>
        <input hidden name="mode" value="search">
        <table bgcolor='#cccccc' style="text-align:center;vertical-align: center;margin:auto">
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

<table id="customers" title="用户信息" class="table"  rownumbers="true" fitcolumns="true" singleselect="true" border="1" style="text-align:center;vertical-align: center;margin:auto">
    <thead style="text-align:center">
    <tr>
        <th field ="1" >客户ID</th>
        <th field ="2" >客户姓名</th>
        <th field ="3" >性别</th>
        <th field ="4" >文化程度</th>
        <th field ="5" >职业</th>
        <th field ="6" >住址</th>
        <th field ="7" >操作</th>
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
                <table>
                <tr>
                    <td>
                        <form method='post' action="customer.do">
                            <input hidden name ="mode" value="delete">
                            <%
                                out.print(" <button type=\"submit\" name=\"id\" value="+rs.getString("id")+">删除</button>");
                            %>
                        </form>
                    </td>
                    <td>
                        <form method=post action="CustomerModify.jsp">
                            <%
                                out.print(" <button type=\"submit\" name=\"id\" value="+rs.getString("id")+">修改</button>");
                            %>
                        </form>
                    </td>
                </tr>
                </table>
            </td>
        </tr>
    </tbody>
    <%
        }
    %>
</table>
</div>



</body>
</html>
