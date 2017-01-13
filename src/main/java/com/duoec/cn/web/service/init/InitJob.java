package com.duoec.cn.web.service.init;

/**
 * 项目启动时自动加载的程序
 * 虽然在其它地方也可以加载,不过为了方便管理,把所有的启动运行的都放在此接口下!
 * Created by ycoe on 16/10/18.
 */
public interface InitJob {
    /**
     * 初始化,实现此接口的应该要支持重复调用!
     */
    void init();

    boolean isLoadFinish();
}
