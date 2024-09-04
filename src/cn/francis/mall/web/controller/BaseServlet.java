package cn.francis.mall.web.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Name: BaseServlet
 * Package: cn.francis.mall.web.controller
 * date: 2024/09/04 - 11:39
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

public class BaseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String methodName = request.getParameter("method");
        try {
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            String url = (String) method.invoke(this, request, response);
            if (url == null || url.trim().isEmpty()) {
                return;
            }
            if (url.startsWith("redirect:")) {
                response.sendRedirect(request.getContextPath() + url.substring(url.indexOf(":") + 1));
            } else {
                request.getRequestDispatcher(url.substring(url.indexOf(":") + 1)).forward(request, response);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}