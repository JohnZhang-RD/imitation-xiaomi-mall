package cn.francis.mall.service;

import cn.francis.mall.domain.Address;
import cn.francis.mall.domain.User;

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
}
