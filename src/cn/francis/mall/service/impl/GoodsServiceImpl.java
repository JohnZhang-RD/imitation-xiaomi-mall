package cn.francis.mall.service.impl;

import cn.francis.mall.dao.GoodsDao;
import cn.francis.mall.dao.impl.GoodsDaoImpl;
import cn.francis.mall.domain.Goods;
import cn.francis.mall.domain.PageBean;
import cn.francis.mall.service.GoodsService;

import java.util.List;

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
}
