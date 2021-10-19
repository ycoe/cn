package com.duoec.web.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * 异步处理
 *
 * @author xuwenzhen
 * @date 2019/12/18
 */
public class AsyncUtils {
    private static final Logger logger = LoggerFactory.getLogger(AsyncUtils.class);

    private AsyncUtils() {
    }

    /**
     * 发起一个异步请求
     *
     * @param supplier 处理
     * @param <D>      返回值
     * @return 异步处理结果
     */
    public static <D> CompletableFuture<D> traceAsync(Supplier<D> supplier) {
        return CompletableFuture.supplyAsync(supplier::get);
    }

    /**
     * 通用，不抛错的异步消息提取
     *
     * @param future 异步数据
     * @param <D>    泛型
     * @return 异步数据
     */
    public static <D> D getFutureData(CompletableFuture<D> future, D defaultValue) {
        D data = getFutureData(future);
        if (data == null) {
            data = defaultValue;
        }
        return data;
    }

    public static <D> D getFutureData(CompletableFuture<D> future) {
        if (future == null) {
            return null;
        }
        try {
            return future.get();
        } catch (Exception e) {
            logger.error("获取异步数据失败", e);
            return null;
        }
    }
}
