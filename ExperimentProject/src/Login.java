import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "Login",
        urlPatterns = "/login.do",
        initParams = {
                @WebInitParam(name = "USER", value = "root"),
                @WebInitParam(name = "PASS", value = "qpalzm"),
                @WebInitParam(name = "DB_URL", value = "jdbc:mysql://localhost/neu_javaweb?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=true")
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
            sql = "SELECT FROM admins WHERE id="+id;
            ResultSet rs = stmt.executeQuery(sql);
            String pw_check = rs.getString("password");
            if(pw_check !=null){
                if(pw_check == pw){
                    if(login_auto.equals("auto")){
                        Cookie cookie_login = new Cookie("admin",id);
                        cookie_login.setMaxAge(7*24*60*60);
                        response.addCookie(cookie_login);
                    }
                    request.getRequestDispatcher("CustomerList.jsp").forward(request,response);
                }else{
                    //弹出提示(未测试)，重定向
                    JOptionPane.showMessageDialog(null, "Wrong password.");
                    out.print("<script language='javascript'>alert('Wrong password.');window.location.href='Login.jsp';</script>");
                }
            }else{
                //弹出提示(未测试)，重定向
                JOptionPane.showMessageDialog(null, "ID not exist.");
                out.print("<script language='javascript'>alert('ID not exist.');window.location.href='Login.jsp';</script>");
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
