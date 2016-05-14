package com.github.walker.taskcenter.control;

import com.github.walker.taskcenter.common.AbsCommnad;
import com.github.walker.taskcenter.common.BasicService;
import com.github.walker.taskcenter.common.ICommand;
import com.github.walker.taskcenter.vo.AppReg;


import com.github.walker.taskcenter.service.AppCallService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 时间调度器，由quartz根据时间表达示触发
 * <p/>
 * Created by Huqingmiao on 2015-5-13.
 */
public class ScheduleCaller {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AppCallService appCallService;


    public void doJob() {
        try {
            //扫描应用注册表及时间调度，获取已启用时间表调度的应用
            List<AppReg> appRegList = appCallService.findScheduleApps();
            //log.info(">>>the count of application: " + appRegList.size());

            for (AppReg appReg : appRegList) {
                log.info(">>>发现应用: " + appReg.getAppCode());

                //如果该应用已被禁用，则不被调度
                if(appReg.getScheduleEnable()== BasicService.ENABLE_FALSE){
                    continue;
                }
                //如果应用正在运行，也不被调度
//                if(appReg.getState()== BasicService.APP_REG_STATE_RUNNING){
//                    return;
//                }

                //下一次的调度时间
                Date nextCallTime = appReg.getNextCallTime();

                //如果下一次的调度时间为空，表明这个应用是新注册的，尚未被调度过， 因此为其计算下一次的调度时间
                if (nextCallTime == null) {
                    nextCallTime = this.calNextCallTime(appReg.getCronExpression());
                    appReg.setNextCallTime(nextCallTime);

                    //将下一次的调度时间更新到调度控制表
                    appCallService.updateScheduler(appReg.getScheduleId(), nextCallTime);
                }

                //如果当前时间晚于下一次调度时后，则调度该应用
                Date currTime = new Date();
                //log.info(">>nextTime "+nextCallTime);

                if (currTime.after(nextCallTime)) {
                    log.info(">>>>>>>>> 准备调度: "+appReg.getAppCode());
                    //交代理立即执行命令
                    ICommand command = AbsCommnad.factory(appReg.getType());
                    command.setApp(appReg);
                    command.execute();
                    log.info(">>>>>>>>> 结束调度: "+appReg.getAppCode());
                }
            }
            appRegList.clear();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    /**
     * 根据quartz的Crontab表达式，计算下一次的调度时间
     *
     * @param cronExpression
     * @return
     */
    private Date calNextCallTime(String cronExpression) {
        int seconds = Integer.parseInt(cronExpression);
        return new Date(new Date().getTime() + seconds * 1000);
    }

}
