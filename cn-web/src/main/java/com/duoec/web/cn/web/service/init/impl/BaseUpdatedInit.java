package com.duoec.web.cn.web.service.init.impl;

import com.duoec.web.cn.core.common.CommonConst;

/**
 * Created by ycoe on 16/11/1.
 */
public abstract class BaseUpdatedInit extends BaseInit {
    /**
     * 是否停止更新
     */
    private boolean stopUpdate = false;

    /**
     * 上次加载时间
     */
    private long time = 0;

    protected void doRunInit() {
    }

    protected void doRunLoaded() {
    }

    @Override
    protected void run() {
        doRunInit();
        stopUpdate = true;

        //加载TDK
        loadData(time);
        doRunLoaded();
        time = System.currentTimeMillis();

        stopUpdate = false;
        //开启词库更新检查
        CommonConst.EXECUTOR_SERVICE.execute(() -> checkUpdate());
    }

    private void checkUpdate() {
        while (true) {
            if (stopUpdate) {
                break;
            }

            //10秒检查一次
            CommonConst.sleep(1000L * getInterval());
            loadData(time);
            time = System.currentTimeMillis();
        }
    }

    protected abstract int getInterval();

    protected abstract void loadData(long time);

    public long getTime() {
        return time;
    }
}
