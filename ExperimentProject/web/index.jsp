<%@ page import="java.sql.*" %><%--
  Created by IntelliJ IDEA.
  User: 54234
  Date: 2018-10-12
  Time: 17:13
  To change this template use File | Settings | File Templates.
--%>
<%@
        page contentType="text/html;charset=UTF-8" language="java"
        import="controller.ConnectionPool"
%>
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
        ConnectionPool pool;
        pool = (ConnectionPool) request.getServletContext().getAttribute("connectionPool");
        Statement stmt = null;
        Connection conn = pool.getConnection();
        try{
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
                if (stmt != null) stmt.close();
                if (conn != null) pool.returnConnection(conn);
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