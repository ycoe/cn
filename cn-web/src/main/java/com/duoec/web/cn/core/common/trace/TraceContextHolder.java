package com.duoec.web.cn.core.common.trace;

/**
 * Created by ycoe on 16/5/4.
 */
public class TraceContextHolder {
    static final ThreadLocal<TraceContext> threadLocal = new ThreadLocal();

    private TraceContextHolder(){}

    public static void setTraceContext(TraceContext context) {
        threadLocal.set(context);
    }

    public static TraceContext getTraceContext() {
        TraceContext traceContext = threadLocal.get();
        if(traceContext == null) {
            traceContext = new TraceContext();
            setTraceContext(traceContext);
        }

        return traceContext;
    }

    public static String getLanguage(){
        return getTraceContext().getLanguage();
    }
}
