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

    private static final long serialVersionUID = 123L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        ConnectionPool pool;
        pool = (ConnectionPool) request.getServletContext().getAttribute("connectionPool");
        Connection conn = null;
        Statement stmt = null;

        Part input = null;
        String ext_name = "";
        for(Part part:request.getParts()){
            if (part.getName().equals("file")){
                input = part;
                //未来改进，使用它获取文件拓展名
                System.out.println("Type:"+part.getContentType());
                ext_name = part.getContentType().split("/")[1];
                /*
                String header = part.getHeader("content-disposition");
                String path = header.substring(header.indexOf("filename=") + 10, header.length() - 1);
                int index = path.lastIndexOf("\\");
                if (index == -1) {
                    index = path.lastIndexOf("/");
                }
                System.out.println(path.substring(index + 1));
                path.substring(index + 1).split("\\.");
                ext_name = path.substring(index + 1).split("\\.")[1];
                 */
            }
        }

        System.out.println("upload finish");


        // 获取文件名
        String name = request.getParameter("fileName")+"."+ext_name;

        // 获取文件的存放目录
        String realPath = request.getServletContext().getRealPath("./") +"upload";
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


        String sql ="";
        try {
            conn = pool.getConnection();
            stmt = conn.createStatement();
            sql = "UPDATE goods SET path =  "+"'"+"/upload/"+name+ "'" + "WHERE ID ="+request.getParameter("fileName");
            stmt.executeUpdate(sql);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println(sql);
            pool.returnConnection(conn);
        }

        // 删除临时文件
        input.delete();

        response.setContentType("text/html;charset=utf-8");

        // 跳转刷新
        request.getServletContext().getRequestDispatcher("/Goods.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Receive the get request,do nothing.");
    }
}
