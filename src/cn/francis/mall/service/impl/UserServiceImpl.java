package cn.francis.mall.service.impl;

import cn.francis.mall.dao.UserDao;
import cn.francis.mall.dao.impl.UserDaoImpl;
import cn.francis.mall.domain.User;
import cn.francis.mall.service.UserService;
import cn.francis.mall.utils.EmailUtils;
import cn.francis.mall.utils.MD5Utils;

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
}
