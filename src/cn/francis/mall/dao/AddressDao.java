package cn.francis.mall.dao;

import cn.francis.mall.domain.Address;

import java.util.List;

/**
 * Name: AddressDao
 * Package: cn.francis.mall.dao
 * date: 2024/09/07 - 16:11
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

public interface AddressDao {
    List<Address> listAddress(Integer id);
}
