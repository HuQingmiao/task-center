package com.github.walker.taskcenter.service;

import com.github.walker.mybatis.paginator.PageBounds;
import com.github.walker.taskcenter.common.BasicService;
import com.github.walker.taskcenter.common.utils.DateTimeUtil;
import com.github.walker.taskcenter.dao.AppCallLogDao;
import com.github.walker.taskcenter.dao.AppRegDao;
import com.github.walker.taskcenter.dao.ScheduleControlDao;
import com.github.walker.taskcenter.vo.AppCallLog;
import com.github.walker.taskcenter.vo.ScheduleControl;
import com.github.walker.taskcenter.vo.AppReg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * 与调度逻辑相关的Service
 * <p/>
 * Created by huqingmiao on 2015-5-13.
 */
@Service
@Transactional
public class AppCallService extends BasicService {

    @Autowired
    private AppRegDao appRegDao;

    @Autowired
    private AppCallLogDao appCallLogDao;

    @Autowired
    private ScheduleControlDao scheduleCtrlDao;


    /**
     * 根据应用程序的编码查找某个设定为事件触发的应用
     *
     * @param appCode
     * @return
     * @throws Exception
     */
    public AppReg findEventApp(String appCode) throws Exception {
        AppReg appReg = appRegDao.findEventApp(appCode);
        return appReg;
    }


    /**
     * 查找所有设定为时间触发的应用
     *
     * @return
     * @throws Exception
     */
    public List<AppReg> findScheduleApps() throws Exception {
        List<AppReg> appRegList = appRegDao.findScheduleApps();
        return appRegList;
    }


    /**
     * 更新指定时间表的下一次的执行时间
     *
     * @param id
     * @param nextCallTime
     * @throws Exception
     */
    public void updateScheduler(Long id, Date nextCallTime) throws Exception {
        ScheduleControl scheduleCtr = new ScheduleControl();
        scheduleCtr.setId(id);
        scheduleCtr.setNextCallTime(DateTimeUtil.toSqlTimestamp(nextCallTime));
        scheduleCtrlDao.updateIgnoreNull(scheduleCtr);
    }


    /**
     * 记录调度日志
     *
     * @param appReg
     * @param action
     * @param rs
     * @param exception
     */
    public void recordCallLog(AppReg appReg, String action, Integer rs, String exception) {

        AppCallLog log = new AppCallLog();
        log.setAppId(appReg.getId());
        log.setAppCode(appReg.getAppCode());
        log.setAction(action);
        log.setTime(DateTimeUtil.currentTime());
        log.setResult(rs);
        log.setException(exception);

        appCallLogDao.save(log);
    }



    public List<AppCallLog> findCallLog(Long appId, int pageNum, int rowcntPerPage) throws Exception {
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        if (appId != null) {
            paramMap.put("appId", appId );
        }
        PageBounds pageBounds = new PageBounds(pageNum, rowcntPerPage);
        return appCallLogDao.find(paramMap, pageBounds);
    }

}