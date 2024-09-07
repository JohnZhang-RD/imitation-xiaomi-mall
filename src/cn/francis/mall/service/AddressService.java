package cn.francis.mall.service;

import cn.francis.mall.domain.Address;

import java.util.List;

/**
 * Name: AddressService
 * Package: cn.francis.mall.service
 * date: 2024/09/07 - 16:09
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

public interface AddressService {
    List<Address> listAddress(Integer id);
}
