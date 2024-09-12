package cn.francis.mall.web.controller;

import cn.dsna.util.images.ValidateCode;
import cn.francis.mall.domain.Address;
import cn.francis.mall.domain.User;
import cn.francis.mall.service.UserService;
import cn.francis.mall.service.impl.UserServiceImpl;
import cn.francis.mall.utils.Base64Utils;
import cn.francis.mall.utils.RandomUtils;
import cn.francis.mall.utils.StringUtils;
import com.alibaba.fastjson2.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String vcode = request.getParameter("vcode");
        String auto = request.getParameter("auto");


        if (StringUtils.isEmpty(username)) {
            request.setAttribute("msg", "用户名不能为空");
            return "/message.jsp";
        }
        if (StringUtils.isEmpty(password)) {
            request.setAttribute("msg", "密码不能为空");
            return "/message.jsp";
        }
        if (StringUtils.isEmpty(vcode)) {
            request.setAttribute("msg", "验证码不能为空");
            return "/message.jsp";
        }

        String code = (String) request.getSession().getAttribute("vcode");
        if (!code.equalsIgnoreCase(vcode)) {
            request.setAttribute("msg", "验证码错误");
            return "/message.jsp";
        }

        try {
            UserService userService = new UserServiceImpl();
            User user = userService.login(username, password);
            request.getSession().setAttribute("user", user);

            if (auto != null) {
                String userInfo = username + "&" + password;
                userInfo = Base64Utils.encode(userInfo);
                Cookie cookie = new Cookie("userInfo", userInfo);
                cookie.setPath(request.getContextPath());
                cookie.setMaxAge(14 * 24 * 60 * 60);
                cookie.setHttpOnly(true);
                response.addCookie(cookie);
            }

            return "redirect:/index.jsp";
        } catch (Exception e) {
            request.setAttribute("msg", "用户登陆失败" + e.getMessage());
            return "/message.jsp";
        }
    }

    // userservlet?method=logOut
    public String logOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        // 失效
        session.invalidate();
        // 删cookie
        Cookie cookie = new Cookie("userInfo", "");
        cookie.setPath(request.getContextPath());
        cookie.setMaxAge(0);
        response.addCookie(cookie);
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

    // userservlet?method=addAddress
    public String addAddress(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String detail = request.getParameter("detail");

        if (StringUtils.isEmpty(name)) {
            request.setAttribute("msg", "收件人为空");
            return "/message.jsp";
        }
        if (StringUtils.isEmpty(phone)) {
            request.setAttribute("msg", "手机号为空");
            return "/message.jsp";
        }
        if (StringUtils.isEmpty(detail)) {
            request.setAttribute("msg", "地址为空");
            return "/message.jsp";
        }
        try {
            User user = (User) request.getSession().getAttribute("user");
            Address address = new Address(null, detail, name, phone, user.getId(), 0);
            UserService userService = new UserServiceImpl();
            userService.saveAddress(address);
            List<Address> addList = userService.listAddress(user.getId());
            request.setAttribute("addList", addList);
            return "redirect:/userservlet?method=getAddress";
        } catch (Exception e) {
            request.setAttribute("msg", "地址添加失败" + e.getMessage());
            return "/message.jsp";
        }

    }


    // userservlet?method=getAddress
    public String getAddress(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/login.jsp";
        }

        try {
            UserService userService = new UserServiceImpl();
            List<Address> addList = userService.listAddress(user.getId());
            request.setAttribute("addList", addList);
            return "/self_info.jsp";
        } catch (Exception e) {
            request.setAttribute("msg", "地址查询失败" + e.getMessage());
            return "/message.jsp";
        }
    }

    // "userservlet?method=deleteAddress&id="+id
    public String deleteAddress(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        String addressId = request.getParameter("id");

        if (StringUtils.isEmpty(addressId)) {
            request.setAttribute("msg", "地址id为空");
            return "/message.jsp";
        }

        try {
            UserService userService = new UserServiceImpl();
            userService.removeAddress(user.getId(), Integer.parseInt(addressId));
            List<Address> addList = userService.listAddress(user.getId());
            request.setAttribute("addList", addList);
            return "redirect:/userservlet?method=getAddress";
        } catch (Exception e) {
            request.setAttribute("msg", "地址删除失败" + e.getMessage());
            return "/message.jsp";
        }
    }

    // "userservlet?method=defaultAddress&id="+id
    public String defaultAddress(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/login.jsp";
        }
        String addId = request.getParameter("id");
        if (StringUtils.isEmpty(addId)) {
            request.setAttribute("msg", "地址id为空");
            return "/message.jsp";
        }
        try {
            UserService userService = new UserServiceImpl();
            userService.modifyDefaultAddress(user.getId(), Integer.parseInt(addId));
            return "redirect:/userservlet?method=getAddress";
        } catch (Exception e) {
            request.setAttribute("msg", "设置默认地址失败" + e.getMessage());
            return "/message.jsp";
        }
    }

    // userservlet?method=updateAddress
    public String updateAddress(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/login.jsp";
        }
        String addId = request.getParameter("id");
        String level = request.getParameter("level");
        String addName = request.getParameter("name");
        String phone = request.getParameter("phone");
        String detail = request.getParameter("detail");

        if (StringUtils.isEmpty(addId)) {
            request.setAttribute("msg", "地址id为空");
            return "/message.jsp";
        }
        if (StringUtils.isEmpty(addId)) {
            request.setAttribute("msg", "地址默认等级为空");
            return "/message.jsp";
        }
        if (StringUtils.isEmpty(addId)) {
            request.setAttribute("msg", "收货人姓名为空");
            return "/message.jsp";
        }
        if (StringUtils.isEmpty(addId)) {
            request.setAttribute("msg", "电话号码为空");
            return "/message.jsp";
        }
        if (StringUtils.isEmpty(addId)) {
            request.setAttribute("msg", "收获地址为空");
            return "/message.jsp";
        }
        try {
            Address address = new Address(Integer.parseInt(addId), detail, addName, phone, user.getId(), Integer.parseInt(level));

            UserService userService = new UserServiceImpl();
            userService.modifyAddress(address);
            return "redirect:/userservlet?method=getAddress";
        } catch (Exception e) {
            request.setAttribute("msg", "更新地址信息失败" + e.getMessage());
            return "/message.jsp";
        }
    }

    /* ================================= 后台内容 =================================*/
    //    /adminLogin
    public String adminLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (StringUtils.isEmpty(username)) {
            request.setAttribute("msg", "用户名为空");
            return "/message.jsp";
        }
        if (StringUtils.isEmpty(password)) {
            request.setAttribute("msg", "密码为空");
            return "/message.jsp";
        }
        try {
            UserService userService = new UserServiceImpl();
            User admin = userService.login(username, password);
            // 做登录校验
            request.getSession().setAttribute("admin", admin);

            request.setAttribute("admin", admin);
            return "/admin/admin.jsp";
        } catch (Exception e) {
            request.setAttribute("msg", "查询用户失败" + e.getMessage());
            return "/message.jsp";
        }
    }

    // getUserList
    public String getUserList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        // 判断是否登录
        User admin = (User) request.getSession().getAttribute("admin");
        if (admin == null) {
            return "redirect:/admin/login.jsp";
        }
        try {
            UserService userService = new UserServiceImpl();
            int flag = 1;
            List<User> userList = userService.listUser(flag);
            JSONArray jsonArray = new JSONArray();
            jsonArray.addAll(userList);

            response.getWriter().write(String.valueOf(jsonArray));
            return null;
        } catch (IOException e) {
            request.setAttribute("msg", "查询用户失败" + e.getMessage());
            return "/message.jsp";
        }
    }

    // userservlet?method=searchUser&username="+username+"&gender="+gender+"&flag="+flag
    // userservlet?method=searchUser&username=ad
    public String searchUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        User admin = (User) request.getSession().getAttribute("admin");
        if (admin == null) {
            return "redirect:/admin/login.jsp";
        }

        String username = request.getParameter("username");
        String gender = request.getParameter("gender");
        String flagStr = request.getParameter("flag");
        int flag = Integer.parseInt(flagStr);

        StringBuilder where = new StringBuilder(" where 1 = 1 ");
        List<Object> params = new ArrayList<>();

        if (!StringUtils.isEmpty(username)) {
            where.append(" and username like ? ");
            params.add("%" + username + "%");
        }
        if (!StringUtils.isEmpty(gender)) {
            where.append(" and gender = ? ");
            params.add(gender);
        }
        if (!StringUtils.isEmpty(flagStr)) {
            where.append(" and flag = ?");
            params.add(flag);
        }
        if (params.isEmpty()) {
            return "admin/userList.jsp";
        }

        try {
            UserService userService = new UserServiceImpl();
            List<User> userList = userService.listUser(where.toString(), params);
            JSONArray jsonArray = new JSONArray();
            jsonArray.addAll(userList);
            response.getWriter().write(String.valueOf(jsonArray));
            return null;
//            return "admin/userList.jsp";
        } catch (Exception e) {
            request.setAttribute("msg", "查询用户失败" + e.getMessage());
            return "/message.jsp";
        }
    }

    // userservlet?method=deleteUser?id="+id
    public String deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User admin = (User) request.getSession().getAttribute("admin");
        if (admin == null) {
            return "redirect:/admin/login.jsp";
        }

        String uid = request.getParameter("id");
        if (StringUtils.isEmpty(uid)) {
            request.setAttribute("msg", "用户id不能为空");
            return "/message.jsp";
        }
        try {
            UserService userService = new UserServiceImpl();
            userService.removeUser(Integer.parseInt(uid));
            return null;
        } catch (Exception e) {
            request.setAttribute("msg", "删除用户失败" + e.getMessage());
            return "/message.jsp";
        }
    }

    // userservlet?method=getInvalidUserList
    public String getInvalidUserList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User admin = (User) request.getSession().getAttribute("admin");
        if (admin == null) {
            return "redirect:/admin/login.jsp";
        }
        response.setContentType("application/json;charset=UTF-8");
        try {
            UserService userService = new UserServiceImpl();
            int flag = 0;
            List<User> userList = userService.listUser(flag);
            JSONArray jsonArray = new JSONArray();
            jsonArray.addAll(userList);
            response.getWriter().write(String.valueOf(jsonArray));
            return null;
        } catch (IOException e) {
            request.setAttribute("msg", "查询无效用户失败" + e.getMessage());
            return "/message.jsp";
        }
    }
}