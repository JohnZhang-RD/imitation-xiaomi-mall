package cn.francis.mall.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Name: MD5Utils
 * Package: cn.francis.mall.utils
 * date: 2024/09/04 - 15:22
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

public class MD5Utils {
    //加密
    public static String md5(String str){
        try {
            //1获取信息摘要
            MessageDigest md5 = MessageDigest.getInstance("md5");
            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);

            //自定义一个算法
            for (int i = 0; i <bytes.length ; i++) {
                bytes[i]-=5;
            }


            //2更新数据
            md5.update(bytes);

            //3加密
            byte[] digest = md5.digest();

            //4返回
            return new BigInteger(1,digest).toString(16);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
