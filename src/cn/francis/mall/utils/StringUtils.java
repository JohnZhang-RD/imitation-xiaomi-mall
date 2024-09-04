package cn.francis.mall.utils;

/**
 * Name: StringUtils
 * Package: cn.francis.mall.utils
 * date: 2024/09/04 - 15:27
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

public class StringUtils {
    public static boolean isEmpty(String str){
        return str == null || str.trim().isEmpty();
    }
}
