package com.melo.util;

import java.io.File;

/**
 * @author zhangxin
 * @date 2025-05-05 20:34
 */
public class FileUtils {
    public static boolean isExist(String filePath,boolean isNew){
        File file = new File(filePath);
        file.setWritable(true,false); //写入权限
        if(!file.exists() && isNew){
            return file.mkdirs();    //新建文件路径
        }
        return false;
    }


}
