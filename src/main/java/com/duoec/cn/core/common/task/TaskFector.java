package com.duoec.cn.core.common.task;

/**
 * Created by ycoe on 16/5/14.
 */
@FunctionalInterface
public interface TaskFector<V> {
    V exec();
}
