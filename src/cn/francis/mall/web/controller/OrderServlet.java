package cn.francis.mall.web.controller;

import cn.francis.mall.domain.*;
import cn.francis.mall.service.AddressService;
import cn.francis.mall.service.CartService;
import cn.francis.mall.service.OrderService;
import cn.francis.mall.service.impl.AddressServiceImpl;
import cn.francis.mall.service.impl.CartServiceImpl;
import cn.francis.mall.service.impl.OrderServiceImpl;
import cn.francis.mall.utils.RandomUtils;
import cn.francis.mall.utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    // "orderservlet?method=addOrder&aid="+$("#address").val()
    public String addOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/login.jsp";
        }

        String aid = request.getParameter("aid");
        if (StringUtils.isEmpty(aid)) {
            request.setAttribute("msg", "地址信息为空");
            return "/message.jsp";
        }

        try {
            OrderService orderService = new OrderServiceImpl();
            CartService cartService = new CartServiceImpl();
            // 获取该用户全部商品
            List<Cart> cartList = cartService.listCart(user.getId());
            if (cartList == null || cartList.isEmpty()) {
                request.setAttribute("msg", "购物车不能为空");
                return "/message.jsp";
            }
            // 计算总金额
            BigDecimal money = new BigDecimal(0);
            for (Cart cart : cartList) {
                money = money.add(cart.getMoney());
            }
            // 订单号生成
            String oid = RandomUtils.createOrderId();
            Order order = new Order(oid, user.getId(), money, "1", LocalDateTime.now(), Integer.parseInt(aid));
            orderService.submitOrder(order, cartList);
            return "/orderSuccess.jsp";
        } catch (NumberFormatException e) {
            request.setAttribute("msg", "订单添加失败" + e.getMessage());
            return "/message.jsp";
        }
    }

    // orderservlet?method=getOrderList
    public String getOrderList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/login.jsp";
        }

        try {
            OrderService orderService = new OrderServiceImpl();
            List<Order> orderList = orderService.listOrder(user.getId());
            request.setAttribute("orderList", orderList);
            return "/orderList.jsp";
        } catch (Exception e) {
            request.setAttribute("msg", "查询订单失败" + e.getMessage());
            return "/message.jsp";
        }
    }

    // orderservlet?method=getOrderDetail&oid="+orderId
    public String getOrderDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/login.jsp";
        }
        String oid = request.getParameter("oid");
        if (StringUtils.isEmpty(oid)) {
            request.setAttribute("msg", "订单id不能为空");
            return "/message.jsp";
        }
        try {
            // 根据用户id和订单号拿到订单信息和该订单内的商品信息
            OrderService orderService = new OrderServiceImpl();
            Order order = orderService.getOrder(user.getId(), oid);

            // 商品详细信息
            List<OrderDetail> orderDetailList = orderService.listOrderDetail(oid);

            request.setAttribute("order", order);
            request.setAttribute("list", orderDetailList);

            return "/orderDetail.jsp";
        } catch (NumberFormatException e) {
            request.setAttribute("msg", "查询订单详情失败" + e.getMessage());
            return "/message.jsp";
        }
    }


    // orderservlet?method=changeStatus&oid="+orderId
    public String changeStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        List<OrderDetail> orderDetailList =
        return null;
    }

    /* ================================= 后台内容 =================================*/

    // orderservlet?method=getAllOrder
    public String getAllOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 登录校验

        try {
            OrderService orderService = new OrderServiceImpl();
            List<Order> orderList = orderService.listOrder();
            request.setAttribute("orderList", orderList);
            return "/admin/showAllOrder.jsp";
        } catch (Exception e) {
            request.setAttribute("msg", "获取全部订单失败" + e.getMessage());
            return "/message.jsp";
        }
    }
}