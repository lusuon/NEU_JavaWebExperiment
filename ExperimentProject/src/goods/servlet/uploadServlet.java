package goods.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import goods.util.ConnectionPool;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@MultipartConfig
@WebServlet(name = "uploadServlet",urlPatterns = "/upload.do")
public class uploadServlet extends HttpServlet {
    ArrayList<String> allow = new ArrayList<String>();
    private static final long serialVersionUID = 123L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        if((boolean)request.getAttribute("allow_upload")) {
            ConnectionPool pool;
            pool = (ConnectionPool) request.getServletContext().getAttribute("connectionPool");
            Connection conn = null;
            Statement stmt = null;

            PrintWriter out = response.getWriter();

            Part input = null;
            String ext_name = "";
            for (Part part : request.getParts()) {
                if (part.getName().equals("file")) {
                    input = part;
                    ext_name = part.getContentType().split("/")[1];
                }
            }

            System.out.println(ext_name);
            for (String s : allow
            ) {
                System.out.print(s + ":");
                System.out.println(s.equals(ext_name));
            }

            // 获取文件名
            String name = request.getParameter("fileName") + "." + ext_name;

            // 获取文件的存放目录
            String realPath = request.getServletContext().getRealPath("./") + "upload";
            File file = new File(realPath);

            System.out.println(realPath);
            if (!file.exists()) {
                file.mkdirs();
            }

            // 输出流
            InputStream inputStream = input.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(new File(file, name));
            int len = -1;
            byte[] bytes = new byte[1024];
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            //未来改进：获取绝对路径作为存入源
            System.out.println(file.getAbsolutePath());
            // 关闭资源
            outputStream.close();
            inputStream.close();

            String sql = "";
            try {
                conn = pool.getConnection();
                stmt = conn.createStatement();
                sql = "UPDATE goods SET path =  " + "'" + "/upload/" + name + "'" + "WHERE ID =" + request.getParameter("fileName");
                stmt.executeUpdate(sql);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println(sql);
                pool.returnConnection(conn);
            }
            // 删除临时文件
            input.delete();
            response.setContentType("text/html;charset=utf-8");
            out.print("<script language='javascript'>alert('Upload successfully.');window.location.href='Goods.jsp';</script>");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Receive the get request,do nothing.");
    }
}
