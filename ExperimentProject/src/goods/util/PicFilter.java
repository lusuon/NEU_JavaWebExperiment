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
        String[] exts = {"jpg","png","bmp","jpeg"};
        for (String s:exts) {
            allow.add(s);
        }
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        boolean allow_upload = false;
        System.out.println("Filtering");
        for(Part part:request.getParts()){
            if (part.getName().equals("file")){
                System.out.println(part.getSize());
                String ext_name = part.getContentType().split("/")[1];
                if(part.getSize()==0) {
                    out.print("<script language='javascript'>alert('Nothing uploaded.');window.location.href='Goods.jsp';</script>");
                }else if(!allow.contains(ext_name)){
                    System.out.println("Not pic");
                    out.print("<script language='javascript'>alert('Only jpg/bmp/png are allowed.');window.location.href='Goods.jsp';</script>");
                }else{
                    allow_upload = true;
                    request.setAttribute("allow_upload",allow_upload);
                }


            }
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
