package cn.francis.mall.web.controller;

import cn.dsna.util.images.ValidateCode;
import cn.francis.mall.domain.User;
import cn.francis.mall.service.UserService;
import cn.francis.mall.service.impl.UserServiceImpl;
import cn.francis.mall.utils.RandomUtils;
import cn.francis.mall.utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Name: UserServlet
 * Package: cn.francis.mall.web.controller
 * date: 2024/09/04 - 11:48
 * Description: 用户Servlet
 *
 * @author Junhui Zhang
 * @version 1.0
 */


@WebServlet("/userservlet")
public class UserServlet extends BaseServlet {
    public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        return "redirect:/index.jsp";
    }

    public String register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rePassword = request.getParameter("repassword");
        String email = request.getParameter("email");
        String gender = request.getParameter("gender");

        if (StringUtils.isEmpty(username)) {
            request.setAttribute("registerMsg", "用户名不能为空");
            return "/register.jsp";
        }
        if (StringUtils.isEmpty(password)) {
            request.setAttribute("registerMsg", "密码不能为空");
            return "/register.jsp";
        }
        if (!password.equals(rePassword)) {
            request.setAttribute("registerMsg", "两次密码不一致");
            return "/register.jsp";
        }
        if (StringUtils.isEmpty(email)) {
            request.setAttribute("registerMsg", "邮箱不能为空");
            return "/register.jsp";
        }
        if (StringUtils.isEmpty(gender)) {
            request.setAttribute("registerMsg", "性别不能为空");
            return "/register.jsp";
        }
        UserService userService = new UserServiceImpl();
        Boolean b = userService.checkUserName(username);
        if (b) {
            request.setAttribute("registerMsg", "用户名已存在");
            return "/register.jsp";
        }
        //flag  0未激活  1激活 2失效   role  0管理员  1会员
        User user = new User(0, username, password, email, gender, 0, 1, RandomUtils.createActiveCode());

        try {
            userService.register(user);
            return "redirect:/registerSuccess.jsp";
        } catch (Exception e) {
            request.setAttribute("registerMsg", "注册失败");
            return "/register.jsp";
        }
    }

    public String checkUserName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        if (StringUtils.isEmpty(username)) {
            response.getWriter().write("1"); // 不可用
            return null;
        }
        UserService userService = new UserServiceImpl();
        Boolean b = userService.checkUserName(username);
        if (b) {
            // 1 已存在 不可用
            response.getWriter().write("1");
            return null;
        }
        response.getWriter().write("0"); // 0 可用
        return null;
    }

    public String code(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", -1);
        ValidateCode validateCode = new ValidateCode(100, 28, 4, 20);
        request.getSession().setAttribute("vcode", validateCode.getCode());
        System.out.println(validateCode.getCode());
        validateCode.write(response.getOutputStream());
        return null;
    }
}