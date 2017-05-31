package com.github.walker.taskcenter.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service 基类, 可在此提供Service层的公共方法，如获取序列号、生成主键等
 * <p/>
 * Created by Huqingmiao on 2015-5-16.
 */
public abstract class BasicService {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    public static final int APP_REG_STATE_IDLE = 0; //空闲

    public static final int APP_REG_STATE_RUNNING = 1;//运行中


    public static final int ENABLE_FALSE = 0;//禁用

    public static final int ENABLE_TRUE = 1; //启用


    public static final String ACTION_CALL_START = "start";//调度开始

    public static final String ACTION_CALL_END = "end"; //调度结束


    public static final int EXEC_FAILED = 0;//任务执行失败

    public static final int EXEC_SUCCESS = 1; //任务执行成功


}
