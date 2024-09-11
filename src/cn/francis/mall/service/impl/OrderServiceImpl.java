package cn.francis.mall.service.impl;

import cn.francis.mall.dao.AddressDao;
import cn.francis.mall.dao.CartDao;
import cn.francis.mall.dao.GoodsDao;
import cn.francis.mall.dao.OrderDao;
import cn.francis.mall.dao.impl.AddressDaoImpl;
import cn.francis.mall.dao.impl.CartDaoImpl;
import cn.francis.mall.dao.impl.GoodsDaoImpl;
import cn.francis.mall.dao.impl.OrderDaoImpl;
import cn.francis.mall.domain.*;
import cn.francis.mall.service.OrderService;
import cn.francis.mall.service.UserService;
import cn.francis.mall.utils.DataSourceUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

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

    @Override
    public List<Order> listOrder(Integer uId) {
        List<Order> orderList = orderDao.listOrder(uId);
        if (orderList == null || orderList.isEmpty()) {
            throw new RuntimeException("无订单");
        }
        // 获取地址列表
        AddressDao addressDao = new AddressDaoImpl();
        List<Address> addressList = addressDao.listAddress(uId);
        if (addressList == null) {
            throw new RuntimeException("无收获地址");
        }
        for (Order order : orderList) {
            for (Address address : addressList) {
                if (Objects.equals(order.getAid(), address.getId())) {
                    order.setAddress(address);
                }
            }
        }
        return orderList;
    }

    @Override
    public Order getOrder(Integer uId, String oId) {
        Order order = orderDao.getOrder(oId);

        AddressDao addressDao = new AddressDaoImpl();
        Address address = addressDao.getAddress(order.getAid());
        order.setAddress(address);

        return order;
    }

    @Override
    public List<OrderDetail> listOrderDetail(String oid) {
        List<OrderDetail> orderDetailList = orderDao.listOrderDetail(oid);
        GoodsDao goodsDao = new GoodsDaoImpl();
        for (OrderDetail orderDetail : orderDetailList) {
            Goods goods = goodsDao.getGoods(orderDetail.getPid());
            orderDetail.setGoods(goods);
        }
        return orderDetailList;
    }

    @Override
    public List<Order> listOrder() {
        int flag = 1;
        List<Order> orderList = orderDao.listOrder();
        UserService userService = new UserServiceImpl();
        List<User> userList = userService.listUser(flag);
        if (orderList == null || orderList.isEmpty()) {
            throw new RuntimeException();
        }
        if (userList == null || userList.isEmpty()) {
            throw new RuntimeException();
        }
        for (Order order : orderList) {
            for (User user : userList) {
                if (Objects.equals(order.getUid(), user.getId())) {
                    order.setUser(user);
                }
            }
        }
        return orderList;
    }
}
