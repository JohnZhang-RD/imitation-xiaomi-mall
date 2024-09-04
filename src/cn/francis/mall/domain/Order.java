package cn.francis.mall.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Name: Order
 * Package: cn.francis.mall.domain
 * date: 2024/09/04 - 11:01
 * Description: 订单
 *
 * @author Junhui Zhang
 * @version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String id;
    private Integer uid;
    private BigDecimal money;
    private String status;
    private LocalDateTime dateTime;
    private Integer aid;
}
