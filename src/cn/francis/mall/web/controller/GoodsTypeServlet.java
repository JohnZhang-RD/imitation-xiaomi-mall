package cn.francis.mall.web.controller;

import cn.francis.mall.domain.GoodsType;
import cn.francis.mall.service.GoodsTypeService;
import cn.francis.mall.service.impl.GoodsTypeServiceImpl;
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
}
