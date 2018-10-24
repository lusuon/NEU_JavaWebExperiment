<%@ page import="java.sql.*" %><%--
  Created by IntelliJ IDEA.
  User: 54234
  Date: 2018-10-12
  Time: 17:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Hello</title>
      <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
  </head>
  <body>
  <style>
      body
      {
          background-image:url("Index.jpg");
          opacity: 0.8;
      }
  </style>
  </head>
    <%
        Statement stmt = null;
        Connection conn = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/neu_javaweb?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false","root", "qpalzm" );
            //Execute a query
            stmt = conn.createStatement();
            Cookie[] cookies = request.getCookies();
            if(cookies!=null){
                for(Cookie cookie:cookies){
                    String name = cookie.getName();
                    String value = cookie.getValue();
                    if(name.equals("admin")){
                        System.out.println("Index page: cookie found");
                        String sql = "SELECT * FROM admins WHERE id='"+value+"'";
                        ResultSet rs = stmt.executeQuery(sql);
                        if(rs.next()){
                            response.sendRedirect("CustomerList.jsp");
                        }
                    }
                }
        }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            {
                //finally block used to close resources
                try {
                    if (stmt != null)
                        stmt.close();
                } catch (SQLException se2) {
                }// nothing we can do
                try {
                    if (conn != null)
                        conn.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }//end finally try
            }
        }
    %>


    <div class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <div class="jumbotron well">
                    <h1>
                        Hello, world!
                    </h1>
                    <p>
                        <span>This is a simple demo for NEU Java Web experiment.It currently includes a module for managing the database of "users".</span>
                    </p>
                    <p>
                        <a class="btn btn-primary btn-large" href="https://github.com/lusuon/NEU_JavaWebExperiment">Find me on github</a>
                    </p>
                    <p>
                    <form role="form" class="form-inline" action="login.do" method="post">
                        <div class="form-group">
                            <label for="exampleInputID">Admin ID</label><input type="text" class="form-control" id="exampleInputID" name="id"/>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">Password</label><input type="password" class="form-control" id="exampleInputPassword1" name='password' />
                        </div>
                        <div class="checkbox">
                            <label><input type="checkbox" name="login" value="auto" />Remember me</label>
                        </div> <button type="submit" class="btn btn-default">Log in</button>
                    </form>
                    </p>
                </div>
            </div>
        </div>
    </div>
  </body>
</html>


<!--
<h1 style="text-align:center">Hello world!</h1>
<form action="login.do" method="post" style="text-align:center;vertical-align: center;">
    <table style="text-align:center;vertical-align: center;margin:auto" >
        <tr style="text-align:center;vertical-align: center;">
            <td style="text-align:center">管理员ID：</td>
            <td style="text-align:center"><input type='text' name='id'></td>
        </tr>

        <tr style="text-align:center;vertical-align: center;">
            <td style="text-align:center">密码：</td>
            <td style="text-align:center"><input type='password' name='password'></td>
        </tr>
    </table>
    自动登录：<input type="checkbox" name="login" value="auto">
    <button type="submit">登录</button>
</form>
<a href='CustomerList.jsp' style="text-align:center">会员列表</a>
-->
