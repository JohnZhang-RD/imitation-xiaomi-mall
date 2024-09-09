package cn.francis.mall.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Name: OrderDetail
 * Package: cn.francis.mall.domain
 * date: 2024/09/04 - 11:02
 * Description: 商品细节
 *
 * @author Junhui Zhang
 * @version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    private Integer id;
    private String oid;
    private Integer pid;
    private Integer num;
    private BigDecimal money;
    private Goods goods;

    public OrderDetail(Integer id, String oid, Integer pid, Integer num, BigDecimal money) {
        this.id = id;
        this.oid = oid;
        this.pid = pid;
        this.num = num;
        this.money = money;
    }
}
