package com.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req= (HttpServletRequest) servletRequest;
        HttpServletResponse resp= (HttpServletResponse) servletResponse;
        HttpSession session=req.getSession();


        String url = req.getRequestURI();
        //例如访问路径为:http://xxx/xxx/login.jsp,取出的就是login.jsp
        String path = url.substring(url.lastIndexOf("/") + 1);
//        System.out.println("默认页面路径为："+path);

        /*获取最后页面的后缀,取出后缀的原因是因为我们页面可能不光是页面,其中包含的一些静态资源也需要向服务器请求,如js/css/image等,如果这些都过滤,那么页面样式将无法使用
         */
        String suffix = path.substring(path.lastIndexOf(".") + 1);
//        System.out.println("其他静态资源文件后缀名："+suffix);

        if ("index.jsp".equals(path) || suffix.equals("js") || suffix.equals("jpg") || suffix.equals("png")|| suffix.equals("css")) {
            filterChain.doFilter(req, resp);
        }else {
            String uid= (String) session.getAttribute("user_id");
            String password= (String) session.getAttribute("user_password");
            System.out.println("[Debug]用户的登录账号为"+uid+"密码为"+password);
//        根据你得到的值进行跳转
            if (password!=null&&uid!=null){
                filterChain.doFilter(servletRequest,servletResponse);
            }else {
                resp.sendRedirect("/index.jsp");
            }
        }

    }

    @Override
    public void destroy() {

    }
}
