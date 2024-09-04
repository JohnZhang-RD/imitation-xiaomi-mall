package cn.francis.mall.dao;

import cn.francis.mall.domain.User;

/**
 * Name: UserDao
 * Package: cn.francis.mall.dao
 * date: 2024/09/04 - 15:44
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

public interface UserDao {
    User selectByUsername(String username);

    void insert(User user);

    int updateFlag(String email, String code);
}
