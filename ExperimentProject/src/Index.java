import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet(name = "Index",
        urlPatterns = "/index.do",
        initParams ={
                @WebInitParam(name = "USER", value = "root"),
                @WebInitParam(name = "PASS", value = "qpalzm"),
                @WebInitParam(name = "DB_URL", value = "jdbc:mysql://localhost/neu_javaweb?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=true")
        }
)
public class Index extends HttpServlet {
    protected void processRequest(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String USER = getServletConfig().getInitParameter("USER");
        String PASS = getServletConfig().getInitParameter("PASS");
        String DB_URL = getServletConfig().getInitParameter("DB_URL");
        Connection conn = null;
        Statement stmt = null;
        String sql; // sql语句

        try{
            Class.forName("com.mysql..jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            Cookie[] cookies = request.getCookies();
            if (cookies!=null){
                for(Cookie cookie:cookies){
                    String name = cookie.getName();
                    String value = cookie.getValue();
                    sql = "SELECT FROM admins WHERE id="+value;
                    ResultSet rs = stmt.executeQuery(sql);
                    if(name.equals("admin")&& rs!=null){
                        //cookie允许登录的条件未完成
                        request.getRequestDispatcher("CustomerList.jsp").forward(request,response);
                        return;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        response.sendRedirect("index.jsp");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        processRequest(request,response);
    }
}
