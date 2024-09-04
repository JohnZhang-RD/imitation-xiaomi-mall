package cn.francis.mall.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Name: Address
 * Package: cn.francis.mall.domain
 * date: 2024/09/04 - 10:57
 * Description: 地址
 *
 * @author Junhui Zhang
 * @version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private Integer id;
    private String detail;
    private String name;
    private String phone;
    private Integer uid;
    private Integer level;
}
