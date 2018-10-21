<%@ page import="java.sql.*" %><%--
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
        Statement stmt = null;
        Connection conn = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/neu_javaweb?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false","root", "qpalzm" );
            //Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            Cookie[] cookies = request.getCookies();
            if(cookies!=null){
                for(Cookie cookie:cookies){
                    String name = cookie.getName();
                    String value = cookie.getValue();
                    if(name.equals("admin")){
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
    <h1 style="text-align:center">Hello world!</h1>
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
