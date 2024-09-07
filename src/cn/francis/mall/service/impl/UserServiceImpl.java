package cn.francis.mall.service.impl;

import cn.francis.mall.dao.UserDao;
import cn.francis.mall.dao.impl.UserDaoImpl;
import cn.francis.mall.domain.Address;
import cn.francis.mall.domain.User;
import cn.francis.mall.service.UserService;
import cn.francis.mall.utils.EmailUtils;
import cn.francis.mall.utils.MD5Utils;

import java.util.List;

/**
 * Name: UserServiceImpl
 * Package: cn.francis.mall.service.impl
 * date: 2024/09/04 - 15:33
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();
    @Override
    public Boolean checkUserName(String username) {
        User user = userDao.selectByUsername(username);
        if (user != null) {
            return true;
        }
        return false;
    }

    @Override
    public void register(User user) {
        user.setPassword(MD5Utils.md5(user.getPassword()));
        userDao.insert(user);
        // 发送邮件
        EmailUtils.sendEmail(user);
    }

    @Override
    public void active(String email, String code) {
        int result = userDao.updateFlag(email, code);
    }

    @Override
    public User login(String username, String password) {
        User user = userDao.selectByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户名不存在");
        }
        if (!MD5Utils.md5(password).equals(user.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        if (user.getFlag() != 1) {
            throw new RuntimeException("账户未激活");
        }
        return user;
    }

    @Override
    public void saveAddress(Address address) {
        userDao.insert(address);
    }

    @Override
    public List<Address> listAddress(Integer uid) {
        return userDao.listAddress(uid);
    }

    @Override
    public void removeAddress(Integer uId, int addressId) {
        userDao.deleteAddress(uId, addressId);
    }
}
