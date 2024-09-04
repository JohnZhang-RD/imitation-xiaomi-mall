package cn.francis.mall.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Name: Goods
 * Package: cn.francis.mall.domain
 * date: 2024/09/04 - 10:59
 * Description: 商品
 *
 * @author Junhui Zhang
 * @version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goods {
    private Integer id;
    private String name;
    private LocalDateTime pubdate;
    private String picture;
    private BigDecimal price;
    private Boolean star;
    private String intro;
    private Integer typeid;
}
