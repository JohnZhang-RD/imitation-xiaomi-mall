package cn.francis.mall.web.controller;

import cn.francis.mall.domain.Address;
import cn.francis.mall.domain.Cart;
import cn.francis.mall.domain.User;
import cn.francis.mall.service.AddressService;
import cn.francis.mall.service.CartService;
import cn.francis.mall.service.impl.AddressServiceImpl;
import cn.francis.mall.service.impl.CartServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Name: OrderServlet
 * Package: cn.francis.mall.web.controller
 * date: 2024/09/07 - 15:56
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */


@WebServlet("/orderservlet")
public class OrderServlet extends BaseServlet {

    // orderservlet?method=getOrderView
    public String getOrderView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/login.jsp";
        }

        try {
            CartService cartService = new CartServiceImpl();
            List<Cart> cartList = cartService.listCart(user.getId());
            AddressService addressService = new AddressServiceImpl();
            List<Address> addList = addressService.listAddress(user.getId());
            request.setAttribute("cartList", cartList);
            request.setAttribute("addList", addList);
            return "/order.jsp";
        } catch (Exception e) {
            request.setAttribute("msg", "订单生成失败" + e.getMessage());
            return "/message.jsp";
        }
    }

}