package com.duoec.web.base.utils;

import java.util.UUID;

/**
 * UUIDUtils
 *
 * @author Luo Huaxing
 * @date 2016/5/3
 */
public class UUIDUtils {
    private UUIDUtils() {
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
