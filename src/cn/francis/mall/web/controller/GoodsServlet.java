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
import java.io.IOException;
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
        System.out.println(goodsName);
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
}
