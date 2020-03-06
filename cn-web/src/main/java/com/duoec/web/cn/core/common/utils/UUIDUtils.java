package com.duoec.web.cn.core.common.utils;

import org.bson.types.ObjectId;

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
        return ObjectId.get().toString();
    }
}
