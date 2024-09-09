package cn.francis.mall.web.filter;

import cn.francis.mall.domain.User;
import cn.francis.mall.service.UserService;
import cn.francis.mall.service.impl.UserServiceImpl;
import cn.francis.mall.utils.Base64Utils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Name: AutoFilter
 * Package: cn.francis.mall.web.filter
 * date: 2024/09/05 - 10:14
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

@WebFilter(filterName = "AutoFilter", value = "/index.jsp")
public class AutoFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            filterChain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userInfo")) {
                    String[] userInfo = Base64Utils.decode(cookie.getValue()).split("&");
                    if (userInfo.length == 2) {
                        String username = userInfo[0];
                        String password = userInfo[1];
                        UserService userService = new UserServiceImpl();
                        try {
                            user = userService.login(username, password);
                            request.getSession().setAttribute("user", user);
                        } catch (Exception e) {
                            Cookie emptyCookie = new Cookie("userInfo", "");
                            emptyCookie.setPath(request.getContextPath());
                            emptyCookie.setMaxAge(0);
                            response.addCookie(cookie);
                        }
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
