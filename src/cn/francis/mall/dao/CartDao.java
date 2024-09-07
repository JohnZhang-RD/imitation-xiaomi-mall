package cn.francis.mall.dao;

import cn.francis.mall.domain.Cart;

import java.util.List;

/**
 * Name: CartDao
 * Package: cn.francis.mall.dao
 * date: 2024/09/07 - 10:30
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

public interface CartDao {
    void insertCart(Cart cart);

    Cart getCart(Integer id, int goodsId);

    void updateCart(Cart cart);

    List<Cart> listCarts(Integer id);

    void deleteCart(Integer id, int goodsId);
}
