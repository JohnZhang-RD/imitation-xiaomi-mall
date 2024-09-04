package cn.francis.mall.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Name: GoodsType
 * Package: cn.francis.mall.domain
 * date: 2024/09/04 - 11:00
 * Description: 商品类型
 *
 * @author Junhui Zhang
 * @version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsType {
    private Integer id;
    private String name;
    private Integer level;
    private Integer parent;
}
