package cn.francis.mall.utils;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

/**
 * Name: FileUtils
 * Package: cn.francis.mall.utils
 * date: 2024/09/10 - 17:31
 * Description: 文件存储路径工具类
 *
 * @author Junhui Zhang
 * @version 1.0
 */

public class FileUtils {

    /**
     * 生成唯一文件名
     * @param filename
     * @return
     */
    public static String makeFilename(String filename) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid + "_" + filename;
    }

    /**
     * 生成存储路径
     * @param basePath
     * @param oldFilename
     * @return
     */
    public static String makePath(String basePath, String oldFilename) {
        String hexCode = Integer.toHexString(oldFilename.hashCode());
        String secondPath = hexCode.charAt(0) + File.separator + hexCode.charAt(1);
        String path = basePath + File.separator + secondPath;
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return path;
    }

    /**
     *
     * @param dir
     * @param hashMap
     */
    public static void listDir(File dir, HashMap<String, String> hashMap) {
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                listDir(file, hashMap);
            } else {
                String uuidFilename = file.getName();
                String filename = uuidFilename.substring(uuidFilename.indexOf("_") + 1);
                hashMap.put(uuidFilename, filename);
            }
        }
    }
}
