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
}
