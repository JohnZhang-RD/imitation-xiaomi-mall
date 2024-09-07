package cn.francis.mall.service.impl;

import cn.francis.mall.dao.CartDao;
import cn.francis.mall.dao.impl.CartDaoImpl;
import cn.francis.mall.domain.Cart;
import cn.francis.mall.domain.Goods;
import cn.francis.mall.service.CartService;
import cn.francis.mall.service.GoodsService;

import java.util.List;

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

    @Override
    public Cart getCart(Integer id, int goodsId) {
        return cartDao.getCart(id, goodsId);
    }

    @Override
    public void updateCart(Cart cart) {
        cartDao.updateCart(cart);
    }

    @Override
    public List<Cart> listCart(Integer id) {
        List<Cart> cartList = cartDao.listCarts(id);
        try {
            GoodsService goodsService = new GoodsServiceImpl();
            for (Cart cart : cartList) {
                Goods goods = goodsService.getGoods(cart.getPid());
                cart.setGoods(goods);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return cartList;
    }

    @Override
    public void removeCart(Integer id, int goodsId) {
        cartDao.deleteCart(id, goodsId);
    }

    @Override
    public void removeAllCart(Integer id) {
        cartDao.deleteAllCart(id);
    }
}
