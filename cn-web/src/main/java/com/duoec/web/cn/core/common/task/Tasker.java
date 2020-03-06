package com.duoec.web.cn.core.common.task;

import com.duoec.web.cn.core.common.CommonConst;
import com.duoec.web.cn.core.common.trace.TraceContext;
import com.duoec.web.cn.core.common.trace.TraceContextHolder;

import java.util.concurrent.Future;

/**
 * Created by ycoe on 16/5/14.
 */
public class Tasker {
    private Tasker(){}

    /**
     * 有需要数据返回的异步方法
     * @param fector
     * @param <V>
     * @return
     */
    public static <V> Future<V> submit(TaskFector<V> fector){
        final TraceContext traceContext = TraceContextHolder.getTraceContext();
        return CommonConst.EXECUTOR_SERVICE.submit(()->{
            TraceContextHolder.setTraceContext(traceContext); //透传TraceContext
            return fector.exec();
        });
    }

    /**
     * 不需要数据返回的异步方法
     * @param runner
     */
    public static void execute(TaskRunner runner){
        final TraceContext traceContext = TraceContextHolder.getTraceContext();
        CommonConst.EXECUTOR_SERVICE.execute(()->{
            TraceContextHolder.setTraceContext(traceContext); //透传TraceContext
            runner.exec();
        });
    }
}
