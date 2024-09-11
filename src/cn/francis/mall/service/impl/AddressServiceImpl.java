package cn.francis.mall.service.impl;

import cn.francis.mall.dao.AddressDao;
import cn.francis.mall.dao.impl.AddressDaoImpl;
import cn.francis.mall.domain.Address;
import cn.francis.mall.service.AddressService;

import java.util.List;

/**
 * Name: AddressServiceImpl
 * Package: cn.francis.mall.service.impl
 * date: 2024/09/07 - 16:09
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

public class AddressServiceImpl implements AddressService {
    private AddressDao addressDao = new AddressDaoImpl();

    @Override
    public List<Address> listAddress(Integer uid) {
        return addressDao.listAddress(uid);
    }

    @Override
    public void removeAddress(Integer uid) {
        addressDao.deleteAddress(uid);
    }
}
