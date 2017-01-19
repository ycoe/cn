package com.duoec.cn.core.common.utils;

import com.google.common.base.Strings;

/**
 * Created by ycoe on 17/1/19.
 */
public class NumberUtils {
    public static boolean isDigits(String str) {
        if (Strings.isNullOrEmpty(str)) {
            return false;
        } else {
            for (int i = 0; i < str.length(); ++i) {
                if (!Character.isDigit(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }
}
