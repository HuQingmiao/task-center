package com.github.walker.taskcenter.control;

import com.github.walker.common.AbsCommnad;
import com.github.walker.common.BasicService;
import com.github.walker.common.ICommand;
import com.github.walker.common.mq.IObserver;
import com.github.walker.common.mq.MqConsumer;
import com.github.walker.common.support.PropertyHolder;
import com.github.walker.taskcenter.service.AppCallService;
import com.github.walker.taskcenter.vo.AppReg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 事件调度器，由mq监听到消息后触发
 * <p/>
 * Created by Huqingmiao on 2015-5-13.
 */
public class EventCaller implements IObserver, Runnable {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AppCallService appCallService;

    /**
     * 建立新线程供MQ监听
     */
    @Override
    public void run() {
        try {
            new MqConsumer().receive(PropertyHolder.getProperty("rabbitmq.queueName"), this);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    @Override
    public void update(String msg) {
        try {
            //从MQ接收到的消息
            int idx = msg.indexOf(":");
            String action = msg.substring(0, idx).trim();
            String msgBody = msg.substring(idx + 1, msg.length()).trim();

            //调度开始,对应的MQ消息为： "start:应用名"
            if (action.equals(BasicService.ACTION_CALL_START)) {
                String appCode = msgBody;
                AppReg appReg = appCallService.findEventApp(appCode);

                //如果该应用已被禁用，则不被调度
                if (appReg.getEventEnable() == BasicService.ENABLE_FALSE) {
                    return;
                }
                //如果应用正在运行，也不被调度
//            if(appReg.getState()== BasicService.APP_REG_STATE_RUNNING){
//                return;
//            }

                ICommand command = AbsCommnad.factory(appReg.getType());
                command.setApp(appReg);
                command.execute();

                //记录调度开始的日志
                appCallService.recordCallLog(appReg, action, null, null);
            }


            //调度结束,对应的MQ消息为： "end:应用名;执行结果;异常"
            if (action.equals(BasicService.ACTION_CALL_END)) {
                String[] items = msgBody.split(";");
                String appCode = items[0].trim();
                Integer result = new Integer(items[1].trim());

                AppReg appReg = appCallService.findEventApp(appCode);

                //记录调度结束的日志
                if (result == BasicService.EXEC_SUCCESS) {
                    appCallService.recordCallLog(appReg, action, BasicService.EXEC_SUCCESS, null);

                } else if (result == BasicService.EXEC_FAILED) {
                    String exception = "";
                    if (items.length == 3) {
                        exception = items[2].trim();
                    }
                    appCallService.recordCallLog(appReg, action, BasicService.EXEC_SUCCESS, exception);
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
