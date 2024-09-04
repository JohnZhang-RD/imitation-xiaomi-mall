package cn.francis.mall.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Name: User
 * Package: cn.francis.mall.domain
 * date: 2024/09/04 - 11:03
 * Description: 用户
 *
 * @author Junhui Zhang
 * @version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String gender;
    private Integer flag;
    private Integer role;
    private String code;
}
