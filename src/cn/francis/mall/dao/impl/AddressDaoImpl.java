package cn.francis.mall.dao.impl;

import cn.francis.mall.dao.AddressDao;
import cn.francis.mall.domain.Address;
import cn.francis.mall.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Name: AddressDaoImpl
 * Package: cn.francis.mall.dao.impl
 * date: 2024/09/07 - 16:11
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

public class AddressDaoImpl implements AddressDao {
    private QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());

    @Override
    public List<Address> listAddress(Integer id) {
        try {
            String sql = " SELECT * FROM tb_address WHERE uid = ? ";
            return queryRunner.query(sql, new BeanListHandler<>(Address.class), id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Address getAddress(Integer aid) {
        String sql = " SELECT * FROM tb_address WHERE id = ? ";
        try {
            return queryRunner.query(sql, new BeanHandler<>(Address.class), aid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAddress(Integer uid) {
        String sql = " DELETE FROM tb_address WHERE uid = ? ";
        try {
            queryRunner.update(sql, uid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
