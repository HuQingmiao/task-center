package com.github.walker.taskcen.dao;

import com.github.walker.common.BasicDao;
import com.github.walker.taskcen.vo.AppReg;

import java.util.List;
import java.util.Map;

/**
 * 应用注册DAO
 * <p/>
 * Created by HuQingmiao
 */
public interface AppRegDao extends BasicDao {

    public AppReg findByCodeOrByName(Map<String, Object> map);

    /**
     * 查找已设定调度时间的应用
     *
     * @return
     */
    public List<AppReg> findScheduleApps();


    /**
     * 根据应用程序编码查找设有事件调度的调用
     *
     * @return
     */
    public AppReg findEventApp(String appCode);
}
