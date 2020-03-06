package com.duoec.web.cn.core.response;

import java.io.PrintWriter;
import java.io.Writer;

/**
 * Created by ycoe on 16/8/11.
 */
public class EmptyPrintWriter extends PrintWriter {

    public EmptyPrintWriter(Writer out) {
        super(out);
    }
}
