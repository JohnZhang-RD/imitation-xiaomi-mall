package cn.francis.mall.service;

import cn.francis.mall.domain.Address;
import cn.francis.mall.domain.User;

import java.util.List;

/**
 * Name: UserService
 * Package: cn.francis.mall.service
 * date: 2024/09/04 - 15:32
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

public interface UserService {
    Boolean checkUserName(String username);

    void register(User user);

    void active(String email, String code);

    User login(String username, String password);

    void saveAddress(Address address);

    List<Address> listAddress(Integer uid);

    void removeAddress(Integer uId, int addressId);

    void modifyDefaultAddress(Integer uId, Integer addId);

    void modifyAddress(Address address);

    List<User> listUser(Integer flag);

    List<User> listUser(String where, List<Object> params);

    void removeUser(Integer uid);
}
