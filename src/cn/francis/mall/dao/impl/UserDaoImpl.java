package cn.francis.mall.dao.impl;

import cn.francis.mall.dao.UserDao;
import cn.francis.mall.domain.User;
import cn.francis.mall.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

/**
 * Name: UserDaoImpl
 * Package: cn.francis.mall.dao.impl
 * date: 2024/09/04 - 15:44
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

public class UserDaoImpl implements UserDao {
    private QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
    @Override
    public User selectByUsername(String username) {
        String sql = "SELECT * FROM tb_user WHERE username = ?";
        try {
            return queryRunner.query(sql, new BeanHandler<>(User.class), username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(User user) {
        String sql = "INSERT INTO tb_user VALUES (null, ?, ?, ?, ?, ?, ?, ?)";
        Object[] params = {user.getUsername(), user.getPassword(), user.getEmail(), user.getGender(), user.getFlag(), user.getRole(), user.getCode()};
        try {
            queryRunner.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateFlag(String email, String code) {
        String sql = "UPDATE tb_user SET flag = 1 WHERE email = ? AND code = ? AND flag = 0";
        try {
            return queryRunner.update(sql, email, code);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
