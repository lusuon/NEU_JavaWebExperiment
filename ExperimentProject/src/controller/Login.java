package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

@WebServlet(name = "controller.Login",
        urlPatterns = "/login.do",
        initParams = {
                @WebInitParam(name = "USER", value = "root"),
                @WebInitParam(name = "PASS", value = "qpalzm"),
                @WebInitParam(name = "DB_URL", value = "jdbc:mysql://localhost/neu_javaweb?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false")
        })
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String USER = getServletConfig().getInitParameter("USER");
        String PASS = getServletConfig().getInitParameter("PASS");
        String DB_URL = getServletConfig().getInitParameter("DB_URL");
        String id = request.getParameter("id");
        String pw = request.getParameter("password");
        String login_auto = request.getParameter("login");
        String sql = "";
        PrintWriter out =response.getWriter();
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            sql = "SELECT * FROM admins WHERE id='"+id+"'";
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println(rs);
            if(rs.next()){
                String pw_check = rs.getString("password");
                if(pw_check.equals(pw)){
                    System.out.println("pw correct");
                    request.getSession().setAttribute("id",request.getParameter("id"));
                    if(login_auto!= null && login_auto.equals("auto")){
                        Cookie cookie_login = new Cookie("admin",id);
                        cookie_login.setMaxAge(7*24*60*60);
                        response.addCookie(cookie_login);
                        request.getSession().setAttribute("auto",true);
                    }
                    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd/hh:mm:ss");
                    String curTime=format.format(new Date());
                    Cookie last_login_time = new Cookie("last_login_time", curTime);
                    response.addCookie(last_login_time);
                    Boolean first_login = true;
                    for (Cookie cookie:request.getCookies()) {
                        if(cookie.getName().equals("login_times")){
                            first_login = false;
                            int times = Integer.parseInt(cookie.getValue())+1;
                            Cookie login_times = new Cookie(cookie.getName(),Integer.toString(times));
                            login_times.setMaxAge(7*24*60*60);
                            response.addCookie(login_times);

                        }
                    }
                    if(first_login) {
                        Cookie login_times = new Cookie("login_times","1");
                        login_times.setMaxAge(7*24*60*60);
                        response.addCookie(login_times);
                    }

                    last_login_time.setMaxAge(7*24*60*60);
                    request.getRequestDispatcher("CustomerList.jsp").forward(request,response);
                }else{
                    //弹出提示，重定向
                    //JOptionPane.showMessageDialog(null, "Wrong password.");
                    out.print("<script language='javascript'>alert('Wrong password.');window.location.href='index.jsp';</script>");
                }
            }else{
                //弹出提示，重定向
                //JOptionPane.showMessageDialog(null, "ID not exist.");
                out.print("<script language='javascript'>alert('ID not exist.');window.location.href='index.jsp';</script>");
            }

        }catch (Exception e){
            e.printStackTrace();

        }finally {
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

    }
}
