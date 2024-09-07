package cn.francis.mall.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Name: Cart
 * Package: cn.francis.mall.domain
 * date: 2024/09/04 - 10:58
 * Description: 购物车
 *
 * @author Junhui Zhang
 * @version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    private Integer id;
    private Integer pid;
    private Integer num;
    private BigDecimal money;
    private Goods goods;

    public Cart(Integer id, Integer pid, Integer num, BigDecimal money) {
        this.id = id;
        this.pid = pid;
        this.num = num;
        this.money = money;
    }
}
