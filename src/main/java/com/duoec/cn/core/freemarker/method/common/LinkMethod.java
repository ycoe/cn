package com.duoec.cn.core.freemarker.method.common;

import com.duoec.cn.core.freemarker.method.BaseFMethod;
import com.duoec.cn.core.freemarker.method.FMethod;

import java.util.List;

/**
 * Created by ycoe on 16/4/28.
 */
@FMethod("link")
public class LinkMethod extends BaseFMethod {
    @Override
    public Object invoke(List arguments) {
        String a = "";
        for (Object arg : arguments) {
            if(a.length() > 0) {
                a += ", ";
            }
            a += "" + arg.toString();
        }
        return a;
    }
}
