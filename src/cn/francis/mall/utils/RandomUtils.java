package cn.francis.mall.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Name: RandomUtils
 * Package: cn.francis.mall.utils
 * date: 2024/09/04 - 16:03
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

public class RandomUtils {
    // 激活码
    public static String createActiveCode() {
        return getTime() + Integer.toHexString(new Random().nextInt(9999999));
    }
    private static String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return simpleDateFormat.format(new Date());
    }
    // 订单号
    public static String createOrderId() {
        return getTime() + new Random().nextInt(9999999);
    }

    public static void main(String[] args) {
        System.out.println(createActiveCode());
    }
}
