package cn.francis.mall.web.controller;

import cn.francis.mall.domain.Goods;
import cn.francis.mall.domain.PageBean;
import cn.francis.mall.service.GoodsService;
import cn.francis.mall.service.impl.GoodsServiceImpl;
import cn.francis.mall.utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Name: GoodsServlet
 * Package: cn.francis.mall.web.controller
 * date: 2024/09/05 - 11:34
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

@WebServlet("/goodsservlet")
public class GoodsServlet extends BaseServlet {

    public String getGoodsListByTypeId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String typeId = request.getParameter("typeId");
        String pageNum = request.getParameter("pageNum");
        String pageSize = request.getParameter("pageSize");
        String goodsName = request.getParameter("goodsName");

        int pageNumDefault = 1;
        int pageSizeDefault = 8;

        try {
            if (!StringUtils.isEmpty(pageNum)) {
                pageNumDefault = Integer.parseInt(pageNum);
                if (pageNumDefault < 1) {
                    pageNumDefault = 1;
                }
            }

            if (!StringUtils.isEmpty(pageSize)) {
                pageSizeDefault = Integer.parseInt(pageSize);
                if (pageSizeDefault < 1) {
                    pageSizeDefault = 8;
                }
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }

        StringBuilder where = new StringBuilder(" where 1 = 1 ");
        List<Object> params = new ArrayList<>();
        if (!StringUtils.isEmpty(typeId)) {
            where.append(" and typeid = ? ");
            params.add(typeId);
        }
        if (!StringUtils.isEmpty(goodsName)) {
            where.append(" and name like ? ");
            params.add("%" + goodsName + "%");
        }
        if (params.isEmpty()) {
            return "/goodsList.jsp";
        }

        try {
            GoodsService goodsService = new GoodsServiceImpl();
            PageBean<Goods> pageBean = goodsService.findByPage(pageNumDefault, pageSizeDefault, where.toString(), params);
            request.setAttribute("typeId", typeId);
            request.setAttribute("goodsName", goodsName);
            request.setAttribute("pageBean", pageBean);
            return "/goodsList.jsp";
        } catch (Exception e) {
            return "redirect:index.jsp";
        }
    }

    // /goodsservlet?method=getGoodsById&id=${g.id}
    public String getGoodsById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (StringUtils.isEmpty(id)) {
            request.setAttribute("msg", "商品id不能为空");
            return "/message.jsp";
        }

        try {
            GoodsService goodsService = new GoodsServiceImpl();
            Goods goods = goodsService.getGoods(Integer.parseInt(id));
            request.setAttribute("goods", goods);
            return "/goodsDetail.jsp";
        } catch (NumberFormatException e) {
            request.setAttribute("msg", "查询失败" + e.getMessage());
            return "/message.jsp";
        }
    }

    /* ================================= 后台内容 =================================*/

    // goodsservlet?method=getGoodsList
    public String getGoodsList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 检查登录

        try {
            // 暂时不做分页
            GoodsService goodsService = new GoodsServiceImpl();
            List<Goods> goodsList = goodsService.listGoods();
            request.setAttribute("goodsList", goodsList);
            return "admin/showGoods.jsp";
        } catch (Exception e) {
            request.setAttribute("msg", "获取商品失败" + e.getMessage());
            return "/message.jsp";
        }
    }

    // goodsservlet?method=addGoods
    public String addGoods(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");

        String typeidStr = request.getParameter("typeid");
        int typeid = Integer.parseInt(typeidStr);

        String pubdateStr = request.getParameter("pubdate");
        LocalDateTime pubdate = LocalDateTime.parse(pubdateStr);

        String priceStr = request.getParameter("price");
        BigDecimal price = new BigDecimal(priceStr);

        String starStr = request.getParameter("star");
        int star = Integer.parseInt(starStr);

        String intro = request.getParameter("intro");

        Part picture = request.getPart("picture");
        String submittedFileName = picture.getSubmittedFileName();

        List<String> limitEnd = new ArrayList<>();
        limitEnd.add(".jpg");
        limitEnd.add(".png");


        return null;
    }
}
