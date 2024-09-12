package cn.francis.mall.service;

import cn.francis.mall.domain.Cart;
import cn.francis.mall.domain.Order;
import cn.francis.mall.domain.OrderDetail;

import java.util.List;

/**
 * Name: OrderService
 * Package: cn.francis.mall.service
 * date: 2024/09/09 - 15:12
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

public interface OrderService {
    void submitOrder(Order order, List<Cart> cartList);

    List<Order> listOrder(Integer uId);

    Order getOrder(Integer uId, String oId);

    List<OrderDetail> listOrderDetail(String oid);

    List<Order> listOrder();

    List<Order> listOrder(String where, List<Object> params);

    List<Order> listOrder(String username);

    List<Order> listOrderByStatus(int status);

    List<Order> listOrder(String username, int status);
}
