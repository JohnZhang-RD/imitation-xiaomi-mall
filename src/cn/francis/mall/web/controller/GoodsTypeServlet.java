package cn.francis.mall.web.controller;

import cn.francis.mall.domain.GoodsType;
import cn.francis.mall.domain.User;
import cn.francis.mall.service.GoodsTypeService;
import cn.francis.mall.service.impl.GoodsTypeServiceImpl;
import cn.francis.mall.utils.StringUtils;
import com.alibaba.fastjson2.JSON;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Name: GoodsTypeServlet
 * Package: cn.francis.mall.web.controller
 * date: 2024/09/05 - 11:31
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

@WebServlet("/goodstypeservlet")
public class GoodsTypeServlet extends BaseServlet {

    public String goodstypelist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        ServletContext application = this.getServletContext();
        String goodsTypeListApp = (String) application.getAttribute("goodsTypeListApp");

        if (goodsTypeListApp != null) {
            response.getWriter().write(goodsTypeListApp);
            return null;
        }

        GoodsTypeService goodsTypeService = new GoodsTypeServiceImpl();
        List<GoodsType> goodsTypeList = goodsTypeService.findByLevel(1);

        String jsonString = JSON.toJSONString(goodsTypeList);
        response.getWriter().write(jsonString);
        application.setAttribute("goodsTypeListApp", jsonString);
        return null;
    }

    /* ================================= 后台内容 =================================*/

    // goodstypeservlet?method=getGoodsType&flag=show
    // goodstypeservlet?method=getGoodsType?flag=add
    public String getGoodsType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 是否登录
        User admin = (User) request.getSession().getAttribute("admin");
        if (admin == null) {
            return "redirect:/login.jsp";
        }
        String flag = request.getParameter("flag");
        if (StringUtils.isEmpty(flag)) {
            request.setAttribute("msg", "flag为空");
            return "/message.jsp";
        }
        try {
            GoodsTypeService goodsTypeService = new GoodsTypeServiceImpl();
            List<GoodsType> goodsTypeList = goodsTypeService.listGoodsType();
            request.setAttribute("goodsTypeList", goodsTypeList);
            if ("show".equals(flag)) {
                return "/admin/showGoodsType.jsp";
            }
            if ("add".equals(flag)) {
                return "admin/addGoodsType.jsp";
            }
            return null;
        } catch (Exception e) {
            request.setAttribute("msg", "获取商品类型失败" + e.getMessage());
            return "/message.jsp";
        }
    }

    // goodstypeservlet?method=addGoodsType
    public String addGoodsType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User admin = (User) request.getSession().getAttribute("admin");
        if (admin == null) {
            return "redirect:/login.jsp";
        }
        return null;
    }
}
