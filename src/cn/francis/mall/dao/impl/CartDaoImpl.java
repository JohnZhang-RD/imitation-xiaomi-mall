package cn.francis.mall.dao.impl;

import cn.francis.mall.dao.CartDao;
import cn.francis.mall.domain.Cart;
import cn.francis.mall.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

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

    @Override
    public Cart getCart(Integer id, int goodsId) {
        String sql = " SELECT * FROM tb_cart WHERE id = ? AND pid = ? ";
        try {
            return queryRunner.query(sql, new BeanHandler<>(Cart.class), id, goodsId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateCart(Cart cart) {
        try {
            String sql = " UPDATE tb_cart SET num = ?, money = ? WHERE id = ? AND pid = ? ";
            Object[] params = {cart.getNum(), cart.getMoney(), cart.getId(), cart.getPid()};
            queryRunner.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Cart> listCarts(Integer id) {
        try {
            String sql = " SELECT * FROM tb_cart WHERE id = ? ";
            return queryRunner.query(sql, new BeanListHandler<>(Cart.class), id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
