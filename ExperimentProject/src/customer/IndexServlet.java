package customer;

import java.io.IOException;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import java.io.PrintWriter;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@WebServlet(
        urlPatterns={"/customer.do"},
        initParams={
                @WebInitParam(name = "USER", value = "root"),
                @WebInitParam(name = "PASS", value = "qpalzm"),
                @WebInitParam(name = "DB_URL", value = "jdbc:mysql://localhost/neu_javaweb?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=true")
        }
)
public class IndexServlet extends javax.servlet.http.HttpServlet {
    //接收参数，决定行为
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        System.out.println("post received");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        //Connection conn  = getDBConnection();
        PrintWriter out=response.getWriter();
        String USER = getServletConfig().getInitParameter("USER");
        String PASS = getServletConfig().getInitParameter("PASS");
        String DB_URL = getServletConfig().getInitParameter("DB_URL");
        HashMap<String,String> paprameters = new HashMap<String,String>();
        Connection conn = null;
        Statement stmt = null;
        String sql; // sql语句
        paprameters.put("id",request.getParameter("id")) ;
        paprameters.put("name",request.getParameter("name")) ;
        paprameters.put("gender", request.getParameter("gender"));
        paprameters.put("job",request.getParameter("job")) ;
        paprameters.put("education",request.getParameter("education")) ;
        paprameters.put("home",request.getParameter("home")) ;
        //获取模式
        String mode = request.getParameter("mode");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //Open a connection
            //Database credentials
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            StringBuilder sbsql = new StringBuilder();
            System.out.println("receiving mode");
            switch(mode) {
                case "add":
                    System.out.println("Adding");
                    if(request.getParameter("id").equals("")) out.print("<script language='javascript'>alert('ID missed');window.location.href='CustomerAdd.jsp';</script>");
                    ResultSet check = stmt.executeQuery("SELECT * FROM customer_info WHERE id ="+request.getParameter("id"));
                    if(check != null ){
                        out.print("<script language='javascript'>alert('ID duplicate');window.location.href='CustomerAdd.jsp';</script>");
                    }else{
                        sbsql.append("INSERT INTO customer_info ").append("VALUES ('").append(paprameters.get("id")+"','").append(paprameters.get("name")+"','").append(paprameters.get("gender")+"','").append(paprameters.get("job")+"','").append(paprameters.get("educaton")+"','").append(paprameters.get("home"));
                        System.out.println(sbsql.toString());
                        stmt.executeUpdate(sbsql.toString());
                        System.out.println("Add success.");
                        request.getRequestDispatcher("CustomerList.jsp").forward(request,response);
                    }
                    break;
                case "modify":
                    sbsql.append("UPDATE customer_info SET ");
                    if(paprameters != null  && paprameters.get("id") != null ) {
                        for (Map.Entry<String, String> entry : paprameters.entrySet()) {
                            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                            if(entry.getValue() != null || !entry.getValue().equals(""))
                                sbsql.append(entry.getKey()+"='"+entry.getValue()+"',");
                        }
                        sbsql.deleteCharAt(sbsql.length()-1);
                        sbsql.append(" WHERE id = '").append(paprameters.get("id")+"'");
                        System.out.println(sbsql.toString());
                        stmt.executeUpdate(sbsql.toString());
                        System.out.println("Modify success.");
                        request.getRequestDispatcher("CustomerList.jsp").forward(request,response);
                    }else{
                        request.getRequestDispatcher("CustomerAdd.jsp").forward(request,response);
                    }
                    break;
                case "delete":
                    System.out.println("delete start");
                    sql = "DELETE FROM customer_info WHERE id=" + paprameters.get("id");

                    System.out.println("sql initialized");
                    stmt.executeUpdate(sql);
                    System.out.println("delete complete");
                    request.getRequestDispatcher("CustomerList.jsp").forward(request,response);
                    break;
                case "search" :
                    sbsql.append("SELECT * FROM customer_info WHERE ");
                    boolean searchDB = false;
                    if(!request.getParameter("id").equals("")){
                        sbsql.append("id='"+request.getParameter("id")+"'");
                        searchDB = true;
                        System.out.println("detect id input");
                    }else if(!request.getParameter("name").equals("")) {
                        searchDB = true;
                        sbsql.append("name LIKE '%" + request.getParameter("name")+"%'");
                        System.out.println("detect name input");
                    }else{
                        sbsql = new StringBuilder();
                        sbsql.append("SELECT * FROM customer_info");
                    }
                    System.out.println(sbsql.toString());
                    ResultSet rs =stmt.executeQuery(sbsql.toString());
                    request.setAttribute("result",rs);
                    request.setAttribute("searchDB",searchDB);
                    request.getRequestDispatcher("CustomerList.jsp").forward(request,response);
                    break;
                    default: System.out.println("Do nothing.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        System.out.println("received get request");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
    }
}
