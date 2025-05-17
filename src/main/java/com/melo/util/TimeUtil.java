package com.melo.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * @author zhangxin
 * @date 2025-05-05 21:05
 */
public class TimeUtil {

    public static String getCurrentTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = LocalDateTime.now().format(formatter);

        // 生成 6 位随机数（不足前补0）
        int randomNum = new Random().nextInt(1_000_000); // 范围 0 - 999999
        String randomStr = String.format("%06d", randomNum);

        return timestamp + randomStr;
    }

}
