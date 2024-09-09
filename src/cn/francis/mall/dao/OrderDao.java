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
}
