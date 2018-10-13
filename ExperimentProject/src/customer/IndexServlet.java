package customer;

import java.io.IOException;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import java.sql.*;
@WebServlet(
        urlPatterns={"/customer.do"},
        initParams={
                @WebInitParam(name = "USER", value = "root"),
                @WebInitParam(name = "PASS", value = "qpalzm"),
                @WebInitParam(name = "DB_URL", value = "jdbc:mysql://localhost/neu_javaweb?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC")
        }
)
public class IndexServlet extends javax.servlet.http.HttpServlet {
    //接收参数，决定行为
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        Connection conn  = getDBConnection();
        Statement stmt = null;
        String sql; // sql语句
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String job = request.getParameter("job");
        String education = request.getParameter("education");
        String home = request.getParameter("home");
        //获取模式
        String mode = request.getParameter("mode");
        try {
            stmt = conn.createStatement();
            switch(mode) {
                case "add":
                    //sql = "SELECT 'ID','NAME','GENDER','JOB','EDUCATION','HOME' FROM customer_info";
                    sql = "INSERT  INTO customer_info('ID','NAME','GENDER','JOB','EDUCATION','HOME' )"+"VALUES('"
                            +id+"','"
                            +name+"','"
                            +gender+"','"
                            +job+"','"
                            +education+"','"
                            +home+"')";
                    stmt.executeQuery(sql);
                    break;
                case "modify":
                    sql = "UPDATE ";
                    stmt.executeQuery(sql);
                    break;
                case "delete":
                    sql = "DELETE WHERE ID";
                    stmt.executeQuery(sql);
                    break;
                case "search" :
                    sql = "";
                    ResultSet rs =stmt.executeQuery(sql);
                    break;
                //Seem no need to use default.
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }




    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }


    public Connection getDBConnection(){
        String USER = getServletConfig().getInitParameter("USER");
        String PASS = getServletConfig().getInitParameter("PASS");
        String DB_URL = getServletConfig().getInitParameter("DB_URL");
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //Open a connection
            //Database credentials
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e2) {
            e2.printStackTrace();
        }

        return conn;
    }


}
