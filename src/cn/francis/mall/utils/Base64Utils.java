package cn.francis.mall.utils;

import java.util.Base64;

/**
 * Name: Base64Utils
 * Package: cn.francis.mall.utils
 * date: 2024/09/04 - 16:19
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

public class Base64Utils {
    //base64编码
    public static String encode(String msg){
        return Base64.getEncoder().encodeToString(msg.getBytes());
    }
    //base64解码
    public static String decode(String msg){
        return new String(Base64.getDecoder().decode(msg));
    }
}
