package cn.francis.mall.dao;

import cn.francis.mall.domain.Address;
import cn.francis.mall.domain.User;

import java.util.List;

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

    void insert(Address address);

    List<Address> listAddress(Integer uid);

    void deleteAddress(Integer uId, int addressId);

    void updateDefaultAddress(Integer uId, Integer addId);

    void updateAddress(Address address);

    List<User> listUser();

    List<User> listUser(String where, List<Object> params);

    void deleteUser(Integer uid);
}
