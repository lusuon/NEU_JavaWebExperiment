import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Logout",urlPatterns = "/logout.do")
public class Logout extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if((Boolean) request.getSession().getAttribute("auto")==null){
            request.getSession().invalidate();
            response.sendRedirect("index.jsp");
        }
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie:cookies){
            System.out.println(cookie.getName());
            if (cookie.getName().equals("admin")){
                System.out.println("cookie get");
                cookie.setMaxAge(0);
                response.addCookie(cookie);//教训，删除cookie必须加入response才有效
                response.sendRedirect("index.jsp");
            }
        }
    }
}
