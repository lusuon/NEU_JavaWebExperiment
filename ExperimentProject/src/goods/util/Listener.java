package goods.util;

import goods.util.ConnectionPool;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;
import java.util.ArrayList;

@WebListener()
public class Listener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {

    // Public constructor is required by servlet spec
    public Listener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */
        //初始化连接池
        ConnectionPool connPool
                = new ConnectionPool("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost/neu_javaweb?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false",
                "root",
                "qpalzm");
        try {
            connPool.createPool();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        ServletContext context = sce.getServletContext();
        context.setAttribute("connectionPool",connPool);


    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
        ServletContext context = sce.getServletContext();
        //销毁连接池
        ConnectionPool connPool = (ConnectionPool)context.getAttribute("connectionPool");
        try {
            connPool.closeConnectionPool();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */

        ArrayList<String> errors = new ArrayList<>();
        HttpSession session = se.getSession();
        session.setAttribute("errors",errors);
    }



    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */

    }

    // -------------------------------------------------------
    // HttpSessionAttributeListener implementation
    // -------------------------------------------------------

    public void attributeAdded(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute 
         is added to a session.
      */
    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute
         is removed from a session.
      */
    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {
      /* This method is invoked when an attibute
         is replaced in a session.
      */
    }

}
