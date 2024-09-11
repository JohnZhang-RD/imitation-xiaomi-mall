package cn.francis.mall.web.controller;

import cn.francis.mall.domain.GoodsType;
import cn.francis.mall.domain.User;
import cn.francis.mall.service.GoodsTypeService;
import cn.francis.mall.service.impl.GoodsTypeServiceImpl;
import cn.francis.mall.utils.StringUtils;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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
        response.setContentType("application/json;charset=UTF-8");
        String flag = request.getParameter("flag");
        try {
            GoodsTypeService goodsTypeService = new GoodsTypeServiceImpl();
            List<GoodsType> goodsTypeList = goodsTypeService.listGoodsType();
            if ("add".equals(flag)) {
                request.setAttribute("goodsTypeList", goodsTypeList);
                return "admin/addGoodsType.jsp";
            }
            JSONArray jsonArray = new JSONArray();
            jsonArray.addAll(goodsTypeList);
            response.getWriter().write(String.valueOf(jsonArray));
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

        String parent = request.getParameter("goodsParent");
        String name = request.getParameter("typename");

        if (StringUtils.isEmpty(parent)) {
            request.setAttribute("msg", "商品类型父类为空");
            return "/message.jsp";
        }
        if (StringUtils.isEmpty(name)) {
            request.setAttribute("msg", "商品类型名称为空");
            return "/message.jsp";
        }

        try {
            GoodsTypeService goodsTypeService = new GoodsTypeServiceImpl();
            GoodsType goodsType = new GoodsType(0, name, null, Integer.parseInt(parent));
            goodsTypeService.saveGoodsType(goodsType);
            return "redirect:/admin/showGoodsType.jsp";
        } catch (NumberFormatException e) {
            request.setAttribute("msg", "商品类型插入失败" + e.getMessage());
            return "/message.jsp";
        }
    }

    // "${pageContext.request.contextPath}/goodstypeservlet?method=deleteGoodsType&id="+id
    public String deleteGoodsType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User admin = (User) request.getSession().getAttribute("admin");
        if (admin == null) {
            return "redirect:/login.jsp";
        }
        String id = request.getParameter("id");
        if (StringUtils.isEmpty(id)) {
            request.setAttribute("msg", "商品类型id为空失败");
            return "/message.jsp";
        }
        try {
            GoodsTypeService goodsTypeService = new GoodsTypeServiceImpl();
            goodsTypeService.removeGoodsType(Integer.parseInt(id));
            return null;
        } catch (NumberFormatException e) {
            request.setAttribute("msg", "删除商品失败" + e.getMessage());
            return "/message.jsp";
        }
    }

    // goodstypeservlet?method=updateGoodsTyep
    public String updateGoodsTyep(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User admin = (User) request.getSession().getAttribute("admin");
        if (admin == null) {
            return "redirect:/login.jsp";
        }

        String id = request.getParameter("id");
        String name = request.getParameter("modalName");
        String level = request.getParameter("modalLevel");
        String parent = request.getParameter("parent");

        try {
            GoodsTypeService goodsTypeService = new GoodsTypeServiceImpl();
            GoodsType goodsType = new GoodsType(Integer.parseInt(id), name, Integer.parseInt(level), Integer.parseInt(parent));
            goodsTypeService.modify(goodsType);

            return "redirect:/admin/showGoodsType.jsp";
        } catch (NumberFormatException e) {
            request.setAttribute("msg", "更新商品类型失败");
            return "/message.jsp";
        }
    }

    // goodstypeservlet?method=searchGoodsType
    public String searchGoodsType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User admin = (User) request.getSession().getAttribute("admin");
        if (admin == null) {
            return "redirect:/login.jsp";
        }
        response.setContentType("application/json;charset=UTF-8");
        String level = request.getParameter("level");
        String name = request.getParameter("name");

        StringBuilder where = new StringBuilder(" where 1 = 1 ");
        List<Object> params = new ArrayList<>();

        if (!StringUtils.isEmpty(level)) {
            where.append(" and level = ? ");
            params.add(Integer.parseInt(level));
        }
        if (!StringUtils.isEmpty(name)) {
            where.append(" and name like ? ");
            params.add("%" + name + "%");
        }

        try {
            GoodsTypeService goodsTypeService = new GoodsTypeServiceImpl();
            List<GoodsType> goodsTypeList = goodsTypeService.listGoodsType(where.toString(), params);
            JSONArray jsonArray = new JSONArray();
            jsonArray.addAll(goodsTypeList);
            response.getWriter().write(String.valueOf(jsonArray));
            return null;
        } catch (Exception e) {
            request.setAttribute("msg", "查询商品类型失败");
            return "/message.jsp";
        }
    }
}
