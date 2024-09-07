package cn.francis.mall.service;

import cn.francis.mall.domain.Cart;

/**
 * Name: CartService
 * Package: cn.francis.mall.service
 * date: 2024/09/07 - 10:29
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

public interface CartService {
    void save(Cart cart);

    Cart getCart(Integer id, int goodsId);

    void updateCart(Cart cart);
}
