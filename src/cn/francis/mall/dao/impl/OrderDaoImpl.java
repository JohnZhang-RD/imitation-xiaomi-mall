package cn.francis.mall.dao.impl;

import cn.francis.mall.dao.OrderDao;
import cn.francis.mall.domain.Order;
import cn.francis.mall.domain.OrderDetail;
import cn.francis.mall.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Name: OrderDaoImpl
 * Package: cn.francis.mall.dao.impl
 * date: 2024/09/09 - 15:22
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

public class OrderDaoImpl implements OrderDao {
    private QueryRunner queryRunner = new QueryRunner();

    @Override
    public void insertOrder(Order order) {
        Connection connection = DataSourceUtils.getConnection();
        String sql = " INSERT INTO tb_order (id, uid, money, status, time, aid) VALUES (?, ?, ?, ?, ?, ?); ";
        Object[] param = {order.getId(), order.getUid(), order.getMoney(), order.getStatus(), order.getTime(), order.getAid()};
        try {
            queryRunner.update(connection, sql, param);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataSourceUtils.closeAll(null, null, connection);
        }
    }

    @Override
    public void insertDetail(OrderDetail orderDetail) {
        Connection connection = DataSourceUtils.getConnection();
        String sql = " INSERT INTO tb_orderdetail (oid, pid, num, money) VALUES (?, ?, ?, ?); ";
        Object[] param = {orderDetail.getOid(), orderDetail.getPid(), orderDetail.getNum(), orderDetail.getMoney()};
        try {
            queryRunner.update(connection, sql, param);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataSourceUtils.closeAll(null, null, connection);
        }
    }

    @Override
    public List<Order> listOrder(Integer uId) {
        Connection connection = DataSourceUtils.getConnection();
        String sql = " SELECT * FROM tb_order WHERE uid = ? ";
        try {
            return queryRunner.query(connection, sql, new BeanListHandler<>(Order.class), uId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order getOrder(String oId) {
        Connection connection = DataSourceUtils.getConnection();
        String sql = " SELECT * FROM tb_order WHERE id = ? ";
        try {
            return queryRunner.query(connection, sql, new BeanHandler<>(Order.class), oId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OrderDetail> getOrderDetail(String oId) {
        Connection connection = DataSourceUtils.getConnection();
        String sql = " SELECT * FROM tb_orderdetail WHERE oid = ? ";
        try {
            return queryRunner.query(connection, sql, new BeanListHandler<>(OrderDetail.class), oId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OrderDetail> listOrderDetail(String oid) {
        Connection connection = DataSourceUtils.getConnection();
        String sql = " SELECT * FROM tb_orderdetail WHERE oid = ? ";
        try {
            return queryRunner.query(connection, sql, new BeanListHandler<>(OrderDetail.class), oid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
