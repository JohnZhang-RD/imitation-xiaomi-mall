package cn.francis.mall.dao;

import cn.francis.mall.domain.GoodsType;

import java.util.List;

/**
 * Name: GoodsTypeDao
 * Package: cn.francis.mall.dao
 * date: 2024/09/05 - 11:46
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

public interface GoodsTypeDao {
    List<GoodsType> select(int i);

    GoodsType getGoodsType(Integer typeid);

    List<GoodsType> listGoodsType();

    void deleteGoodsType(int typeId);

    void updateGoodsType(GoodsType goodsType);

    void insertGoodsType(GoodsType goodsType);

    List<GoodsType> listGoodsType(String where, List<Object> params);
}
