package cn.francis.mall.web.controller;

import cn.francis.mall.service.UserService;
import cn.francis.mall.service.impl.UserServiceImpl;
import cn.francis.mall.utils.Base64Utils;
import cn.francis.mall.utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Name: ActiveServlet
 * Package: cn.francis.mall.web.controller
 * date: 2024/09/04 - 16:20
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */


@WebServlet("/activate")
public class ActiveServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("e");
        String code = request.getParameter("c");

        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(code)) {
            request.setAttribute("msg", "激活失败");
            request.getRequestDispatcher("/message.jsp").forward(request, response);
            return;
        }
        email = Base64Utils.decode(email);
        code = Base64Utils.decode(code);

        try {
            UserService userService = new UserServiceImpl();
            userService.active(email, code);
            request.setAttribute("msg", "激活成功");
            request.getRequestDispatcher("/message.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msg", "激活失败" + e.getMessage());
            request.getRequestDispatcher("/message.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}