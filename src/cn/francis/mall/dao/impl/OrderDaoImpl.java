package cn.francis.mall.dao.impl;

import cn.francis.mall.dao.OrderDao;
import cn.francis.mall.domain.Order;
import cn.francis.mall.domain.OrderDetail;
import cn.francis.mall.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;

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
        Object[] param = {order.getId(), order.getUid(), order.getMoney(), order.getStatus(), order.getDateTime(), order.getAid()};
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
}
