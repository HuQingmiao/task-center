package com.github.walker.taskcenter.common;

import com.github.walker.taskcenter.vo.AppReg;

/**
 * 调度命令的接口，每个实例对应一个被调度的应用程序
 * <p/>
 * Created by Huqingmiao on 2015-5-14.
 */
public interface ICommand {

    /**
     * 指定应用注册信息
     *
     * @param app
     */
    public void setApp(AppReg app);

    /**
     * 执行命令
     *
     * @return
     */
    public void execute();


    /**
     * 获取执行状态
     *
     * @return
     */
    public String getState();
}
