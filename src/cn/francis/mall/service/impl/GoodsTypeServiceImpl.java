package cn.francis.mall.service.impl;

import cn.francis.mall.dao.GoodsTypeDao;
import cn.francis.mall.dao.impl.GoodsTypeDaoImpl;
import cn.francis.mall.domain.GoodsType;
import cn.francis.mall.service.GoodsTypeService;

import java.util.List;

/**
 * Name: GoodsTypeServiceImpl
 * Package: cn.francis.mall.service.impl
 * date: 2024/09/05 - 11:43
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

public class GoodsTypeServiceImpl implements GoodsTypeService {
    private GoodsTypeDao goodsTypeDao = new GoodsTypeDaoImpl();

    @Override
    public List<GoodsType> findByLevel(int i) {
        return goodsTypeDao.select(i);
    }

    @Override
    public GoodsType getGoodsType(Integer typeid) {
        return goodsTypeDao.getGoodsType(typeid);
    }

    @Override
    public List<GoodsType> listGoodsType() {
        return goodsTypeDao.listGoodsType();
    }

    @Override
    public void removeGoodsType(int typeId) {
        // TODO 级联删除商品、或采用其他方法
        goodsTypeDao.deleteGoodsType(typeId);
    }

    @Override
    public void modify(GoodsType goodsType) {
        goodsTypeDao.updateGoodsType(goodsType);
    }

    @Override
    public void saveGoodsType(GoodsType goodsType) {
        GoodsTypeService goodsTypeService = new GoodsTypeServiceImpl();
        GoodsType parentGoodsType = goodsTypeService.getGoodsType(goodsType.getParent());
        goodsType.setLevel(parentGoodsType.getLevel() + 1);
        goodsTypeDao.insertGoodsType(goodsType);
    }

    @Override
    public List<GoodsType> listGoodsType(String where, List<Object> params) {
        return goodsTypeDao.listGoodsType(where, params);
    }
}
