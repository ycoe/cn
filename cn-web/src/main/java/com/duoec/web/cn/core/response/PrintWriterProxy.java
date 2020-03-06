package com.duoec.web.cn.core.response;

import java.io.PrintWriter;

/**
 * Created by ycoe on 16/5/13.
 */
public class PrintWriterProxy extends PrintWriter {
    private StringBuilder sb = new StringBuilder();

    public PrintWriterProxy(PrintWriter pw) {
        super(pw);
    }

    //截获写内容写入buffer
    @Override
    public void write(String c) {
        super.write(c);
        sb.append(c);
    }

    @Override
    public void write(char buf[]) {
        super.write(buf);
        sb.append(buf);
    }

    @Override
    public void write(int c) {
        super.write(c);
        sb.append(c);
    }

    public void reset() {
        sb.delete(0, sb.length());
    }

    public String getContent() {
        return sb.toString();
    }
}
