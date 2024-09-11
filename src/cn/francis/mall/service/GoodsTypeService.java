package cn.francis.mall.service;

import cn.francis.mall.domain.GoodsType;

import java.util.List;

/**
 * Name: GoodsTypeService
 * Package: cn.francis.mall.service
 * date: 2024/09/05 - 11:43
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

public interface GoodsTypeService {
    List<GoodsType> findByLevel(int i);

    GoodsType getGoodsType(Integer typeid);

    List<GoodsType> listGoodsType();

    void removeGoodsType(int typeId);

    void modify(GoodsType goodsType);

    void saveGoodsType(GoodsType goodsType);

    List<GoodsType> listGoodsType(String where, List<Object> params);
}
