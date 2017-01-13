package com.duoec.cn.web.service.init.impl;

import com.duoec.cn.core.common.CommonConst;
import com.duoec.cn.web.service.init.InitJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

/**
 * Created by ycoe on 16/10/18.
 */
public abstract class BaseInit implements InitJob {
    private static final Logger logger = LoggerFactory.getLogger(BaseInit.class);

    /**
     * 是否加载完成
     */
    private boolean loadFinish = false;

    @PostConstruct
    @Override
    public void init() {
        final Class jobClass = this.getClass();
        CommonConst.EXECUTOR_SERVICE.execute(() -> {
            while (!isReady()) {
                CommonConst.sleep(10);
            }
            long t1 = System.currentTimeMillis();
            loadFinish = false;
            run();
            loadFinish = true;
            logger.info("{}.init(),耗时{}ms", jobClass.getSimpleName(), System.currentTimeMillis() - t1);
        });
    }

    /**
     * 检查依赖是否准备好
     *
     * @return
     */
    protected boolean isReady() {
        return true;
    }

    @Override
    public boolean isLoadFinish() {
        return loadFinish;
    }

    protected abstract void run();
}
