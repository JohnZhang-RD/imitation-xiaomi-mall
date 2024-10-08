package cn.francis.mall.service;

import cn.francis.mall.domain.Cart;
import cn.francis.mall.domain.Goods;
import cn.francis.mall.domain.PageBean;

import java.util.List;

/**
 * Name: GoodsService
 * Package: cn.francis.mall.service
 * date: 2024/09/05 - 16:16
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

public interface GoodsService {
    PageBean<Goods> findByPage(int pageNumDefault, int pageSizeDefault, String where, List<Object> params);

    Goods getGoods(int id);

    List<Goods> listGoods();

    void saveGoods(Goods goods);

    void removeGoods(Integer id);

    void modifyGoods(Goods goods);

    List<Goods> listGoods(String where, List<Object> params);
}
