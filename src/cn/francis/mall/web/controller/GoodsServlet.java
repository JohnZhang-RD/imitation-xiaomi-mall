package cn.francis.mall.web.controller;

import cn.francis.mall.domain.GoodsType;
import cn.francis.mall.service.GoodsTypeService;
import cn.francis.mall.service.impl.GoodsTypeServiceImpl;
import com.alibaba.fastjson2.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
public class GoodsServlet {
    public String getGoodsListByTypeId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return null;
    }
}
