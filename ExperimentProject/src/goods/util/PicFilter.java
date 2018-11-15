package goods.util;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

@WebFilter(filterName = "PicFilter",urlPatterns = "/upload.do")
public class PicFilter implements Filter {
    ArrayList<String> allow = new ArrayList<String>();

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        String[] exts = {"jpg","png","bmp"};
        for (String s:exts) {
            allow.add(s);
        }
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        System.out.println("Filtering");
        for(Part part:request.getParts()){
            if (part.getName().equals("file")){
                if(part.getSize()==0) out.print("<script language='javascript'>alert('Nothing uploaded.');window.location.href='Goods.jsp';</script>");

                String header = part.getHeader("content-disposition");
                String path = header.substring(header.indexOf("filename=") + 10, header.length() - 1);
                int index = path.lastIndexOf("\\");
                if (index == -1) {
                    index = path.lastIndexOf("/");
                }
                System.out.println(path.substring(index + 1));
                path.substring(index + 1).split("\\.");
                String ext_name = path.substring(index + 1).split("\\.")[1];


                System.out.println(allow.contains(ext_name));
                if(!allow.contains(ext_name)){
                    out.print("<script language='javascript'>alert('Only pictures are allowed to upload.');window.location.href='Goods.jsp';</script>");
                    response.sendRedirect("Goods.jsp");
                }
            }
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
