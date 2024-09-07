package cn.francis.mall.service.impl;

import cn.francis.mall.dao.CartDao;
import cn.francis.mall.dao.impl.CartDaoImpl;
import cn.francis.mall.domain.Cart;
import cn.francis.mall.service.CartService;

/**
 * Name: CartServiceImpl
 * Package: cn.francis.mall.service.impl
 * date: 2024/09/07 - 10:29
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

public class CartServiceImpl implements CartService {
    private CartDao cartDao = new CartDaoImpl();

    @Override
    public void save(Cart cart) {
        cartDao.insertCart(cart);
    }
}
