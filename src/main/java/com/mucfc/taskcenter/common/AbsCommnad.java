package com.mucfc.taskcenter.common;

import com.mucfc.taskcenter.common.support.GlobalContext;
import com.mucfc.taskcenter.common.utils.DateTimeUtil;
import com.mucfc.taskcenter.dao.AppCallLogDao;
import com.mucfc.taskcenter.dao.ScheduleControlDao;
import com.mucfc.taskcenter.vo.AppCallLog;
import com.mucfc.taskcenter.vo.AppReg;
import com.mucfc.taskcenter.vo.ScheduleControl;
import com.mucfc.taskcenter.service.HttpCommand;
import com.mucfc.taskcenter.service.ShellCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by huqingmiao on 2015-5-27.
 */
public abstract class AbsCommnad implements ICommand {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    protected static final int COMMAND_TYPE_SHELL = 1;

    protected static final int COMMAND_TYPE_HTTP = 2;


    private AppReg app;

    @Autowired
    private AppCallLogDao appCallLogDao;

    @Autowired
    private ScheduleControlDao scheduleCtrlDao;

    protected AbsCommnad() {
    }

    public static ICommand factory(Integer type) {

        if (type.intValue() == COMMAND_TYPE_SHELL) {
            return (ShellCommand) GlobalContext.context.getBean("shellCommand");
        }

        if (type.intValue() == COMMAND_TYPE_HTTP) {
            return (HttpCommand) GlobalContext.context.getBean("httpCommand");
        }

        return null;
    }


    @Override
    public void setApp(AppReg app) {
        this.app = app;
    }
    
    public AppReg getApp(){
        return this.app;
    }
    
    @Override
    public void execute() {
        try {
            //调度应用程序
            String commandLine = app.getCommand();
            Runtime.getRuntime().exec(commandLine);

            //调度后做相应记录
            this.doAfterCall(app);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    @Override
    public String getState() {
        return null;
    }


    /**
     * 调度应用后,记录日志。
     *
     * @param appReg
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED)
    protected void doAfterCall(AppReg appReg) throws Exception {

        AppCallLog log = new AppCallLog();
        log.setAppId(appReg.getId());
        log.setAppCode(appReg.getAppCode());
        log.setAction(BasicService.ACTION_CALL_START);
        log.setTime(DateTimeUtil.currentTime());
        appCallLogDao.save(log);

        //如果是时间片调度，则需要更新下一次的执行时间
        Date nextCallTime = appReg.getNextCallTime();
        if (nextCallTime != null) {
            //更新下一次的调度时间
            nextCallTime = this.calNextCallTime(appReg.getCronExpression(), log.getTime());
            ScheduleControl scheduleCtr = new ScheduleControl();
            scheduleCtr.setId(appReg.getScheduleId());
            scheduleCtr.setNextCallTime(DateTimeUtil.toSqlTimestamp(nextCallTime));
            scheduleCtrlDao.updateIgnoreNull(scheduleCtr);
        }
    }


    /**
     * 根据quartz的Crontab表达式及上次的调度时间，计算下一次的调度时间
     *
     * @param cronExpression
     * @return
     */
    protected Date calNextCallTime(String cronExpression, Date lastCallTime) {
        int seconds = Integer.parseInt(cronExpression);
        return new Date(lastCallTime.getTime() + seconds * 1000);
    }
}
