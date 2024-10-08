package cn.francis.mall.dao;

import cn.francis.mall.domain.Order;
import cn.francis.mall.domain.OrderDetail;

import java.util.List;

/**
 * Name: OrderDao
 * Package: cn.francis.mall.dao
 * date: 2024/09/09 - 15:22
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

public interface OrderDao {
    void insertOrder(Order order);

    void insertDetail(OrderDetail orderDetail);

    List<Order> listOrder(Integer uId);

    Order getOrder(String oId);

    List<OrderDetail> getOrderDetail(String oId);

    List<OrderDetail> listOrderDetail(String oid);

    List<Order> listOrder();

    List<Order> listOrder(String where, List<Object> params);

    List<Order> listOrderByStatus(int status);

    List<Order> listOrder(Integer uid, int status);

    void updateOrderStatus(String oid, int status);
}
