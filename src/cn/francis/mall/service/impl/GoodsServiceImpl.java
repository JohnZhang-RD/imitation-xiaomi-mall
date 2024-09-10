package cn.francis.mall.service.impl;

import cn.francis.mall.dao.GoodsDao;
import cn.francis.mall.dao.impl.GoodsDaoImpl;
import cn.francis.mall.domain.Cart;
import cn.francis.mall.domain.Goods;
import cn.francis.mall.domain.GoodsType;
import cn.francis.mall.domain.PageBean;
import cn.francis.mall.service.GoodsService;
import cn.francis.mall.service.GoodsTypeService;

import java.util.List;
import java.util.Objects;

/**
 * Name: GoodsServiceImpl
 * Package: cn.francis.mall.service.impl
 * date: 2024/09/05 - 16:16
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

public class GoodsServiceImpl implements GoodsService {
    private GoodsDao goodsDao = new GoodsDaoImpl();

    @Override
    public PageBean<Goods> findByPage(int pageNumDefault, int pageSizeDefault, String where, List<Object> params) {
        long totalSize = goodsDao.getCount(where, params);
        List<Goods> data = goodsDao.selectByPage(pageNumDefault, pageSizeDefault, where, params);
        return new PageBean<>(pageNumDefault, pageSizeDefault, totalSize, data);
    }

    @Override
    public Goods getGoods(int id) {
        Goods goods = goodsDao.getGoods(id);
        if (goods != null) {
            Integer typeid = goods.getTypeid();
            GoodsTypeService goodsTypeService = new GoodsTypeServiceImpl();
            GoodsType goodsType = goodsTypeService.getGoodsType(typeid);
            goods.setGoodsType(goodsType);
        }
        return goods;
    }

    @Override
    public List<Goods> listGoods() {
        List<Goods> goodsList = goodsDao.listGoods();
        GoodsTypeService goodsTypeService = new GoodsTypeServiceImpl();
        List<GoodsType> goodsTypeList = goodsTypeService.listGoodsType();
        for (Goods goods : goodsList) {
            for (GoodsType goodsType : goodsTypeList) {
                if (Objects.equals(goods.getTypeid(), goodsType.getId())) {
                    goods.setGoodsType(goodsType);
                }
            }
        }
        return goodsList;
    }

}
