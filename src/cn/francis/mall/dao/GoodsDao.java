package cn.francis.mall.dao;

import cn.francis.mall.domain.Cart;
import cn.francis.mall.domain.Goods;

import java.util.List;

/**
 * Name: GoodsDao
 * Package: cn.francis.mall.dao
 * date: 2024/09/05 - 16:22
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

public interface GoodsDao {
    long getCount(String where, List<Object> params);

    List<Goods> selectByPage(int pageNumDefault, int pageSizeDefault, String where, List<Object> params);

    Goods getGoods(int id);

    List<Goods> listGoods();
}
