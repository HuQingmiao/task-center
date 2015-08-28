package com.mucfc.taskcenter.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MVC Controller基类，可在此提供Controller层的公共方法，如果上传、下载、参数转换等
 * <p/>
 * Created by Huqingmiao on 2015-5-16.
 */
public abstract class BasicController {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

}
