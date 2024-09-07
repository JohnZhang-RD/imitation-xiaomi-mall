package cn.francis.mall.dao.impl;

import cn.francis.mall.dao.CartDao;
import cn.francis.mall.domain.Cart;
import cn.francis.mall.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;

/**
 * Name: CartDaoImpl
 * Package: cn.francis.mall.dao.impl
 * date: 2024/09/07 - 10:30
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

public class CartDaoImpl implements CartDao {
    private QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());

    @Override
    public void insertCart(Cart cart) {
        String sql = " INSERT INTO tb_cart (id, pid, num, money) VALUES (?, ?, ?, ?) ";
        Object[] params = {cart.getId(), cart.getPid(), cart.getNum(), cart.getMoney()};
        try {
            queryRunner.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
