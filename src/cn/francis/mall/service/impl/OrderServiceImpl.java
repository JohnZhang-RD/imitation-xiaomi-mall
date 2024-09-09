package cn.francis.mall.service.impl;

import cn.francis.mall.dao.CartDao;
import cn.francis.mall.dao.OrderDao;
import cn.francis.mall.dao.impl.CartDaoImpl;
import cn.francis.mall.dao.impl.OrderDaoImpl;
import cn.francis.mall.domain.Cart;
import cn.francis.mall.domain.Order;
import cn.francis.mall.domain.OrderDetail;
import cn.francis.mall.service.OrderService;
import cn.francis.mall.utils.DataSourceUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * Name: OrderServiceImpl
 * Package: cn.francis.mall.service.impl
 * date: 2024/09/09 - 15:12
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = new OrderDaoImpl();

    @Override
    public void submitOrder(Order order, List<Cart> cartList) {
        try {
            DataSourceUtils.begin();

            // 添加订单
            orderDao.insertOrder(order);
            // 向订单详情表添加数据
            for (Cart cart : cartList) {
                OrderDetail orderDetail = new OrderDetail(0, order.getId(), cart.getPid(), cart.getNum(), cart.getMoney());
                orderDao.insertDetail(orderDetail);
            }
            // 清空购物车
            CartDao cartDao = new CartDaoImpl();
            cartDao.deleteAllCart(order.getUid());


            DataSourceUtils.commit();
        } catch (SQLException e) {
            try {
                DataSourceUtils.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException();
        } finally {
            try {
                DataSourceUtils.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
