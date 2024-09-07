package cn.francis.mall.web.controller;

import cn.francis.mall.domain.Cart;
import cn.francis.mall.domain.Goods;
import cn.francis.mall.domain.User;
import cn.francis.mall.service.CartService;
import cn.francis.mall.service.GoodsService;
import cn.francis.mall.service.impl.CartServiceImpl;
import cn.francis.mall.service.impl.GoodsServiceImpl;
import cn.francis.mall.utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Name: CartServlet
 * Package: cn.francis.mall.web.controller
 * date: 2024/09/07 - 10:07
 * Description: 购物车servlet
 *
 * @author Junhui Zhang
 * @version 1.0
 */

@WebServlet("/cartservlet")
public class CartServlet extends BaseServlet {

    // /cartservlet?method=addCart&goodsId=${goods.id}&number=1
    public String addCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 判断是否登录
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/login.jsp";
        }

        String goodsId = request.getParameter("goodsId");
        String number = request.getParameter("number");

        if (StringUtils.isEmpty(goodsId)) {
            request.setAttribute("msg", "商品id不能为空");
            return "/message.jsp";
        }
        int num = 1;
        if (!StringUtils.isEmpty(number)) {
            try {
                num = Integer.parseInt(number);
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            CartService cartService = new CartServiceImpl();
            int goodsIdInt = Integer.parseInt(goodsId);
            GoodsService goodsService = new GoodsServiceImpl();
            Goods goods = goodsService.getGoods(goodsIdInt);
            // 查看购物车是否已经有该商品
            Cart cart = cartService.getCart(user.getId(), goodsIdInt);
            if (cart != null) {
                cart.setNum(cart.getNum() + num);
                cart.setMoney(goods.getPrice().multiply(new BigDecimal(cart.getNum())));
                cartService.updateCart(cart);
            } else{
                cart = new Cart(user.getId(), goodsIdInt, num, goods.getPrice().multiply(new BigDecimal(num)));
                cartService.save(cart);
            }
            return "redirect:/cartSuccess.jsp";
        } catch (NumberFormatException e) {
            request.setAttribute("msg", "添加购物车失败" + e.getMessage());
            return "/message.jsp";
        }
    }

    // /cartservlet?method=getCart
    public String getCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 判断用户是否登录
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/login.jsp";
        }

        try {
            CartService cartService = new CartServiceImpl();
            List<Cart> cartList = cartService.listCart(user.getId());
            request.setAttribute("cartList", cartList);
            return "/cart.jsp";
        } catch (Exception e) {
            request.setAttribute("msg", "查看购物车失败" + e.getMessage());
            return "/message.jsp";
        }
    }

    // "cartservlet?method=addCartAjax&goodsId="+pid+"&number="+num,
    public String addCartAjax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/login.jsp";
        }

        String goodsId = request.getParameter("goodsId");
        String number = request.getParameter("number");

        if (StringUtils.isEmpty(goodsId)) {
            request.setAttribute("msg", "商品id不能为空");
            return "/message.jsp";
        }

        int goodsIdInt = Integer.parseInt(goodsId);
        int numberInt = Integer.parseInt(number);
        try {
            CartService cartService = new CartServiceImpl();
            if (numberInt == 0) {
                // 删除
                cartService.removeCart(user.getId(), goodsIdInt);
            }

            if (numberInt == 1 || numberInt == -1) {
                Cart cart = cartService.getCart(user.getId(), goodsIdInt);
                if (cart != null) {
                    GoodsService goodsService = new GoodsServiceImpl();
                    Goods goods = goodsService.getGoods(goodsIdInt);
                    cart.setNum(cart.getNum() + numberInt);
                    cart.setMoney(goods.getPrice().multiply(new BigDecimal(cart.getNum())));
                    cartService.updateCart(cart);
                }
            }
            return null;
        } catch (Exception e) {
            request.setAttribute("msg", "更新购物车失败" + e.getMessage());
            return "/message.jsp";
        }
    }
}
