package com.github.walker.taskcenter.control;


import com.github.walker.taskcenter.common.*;
import com.github.walker.taskcenter.common.support.PagerControl;
import com.github.walker.taskcenter.common.utils.PropertyUtil;
import com.github.walker.mybatis.paginator.PageList;
import com.github.walker.taskcenter.service.AppCallService;
import com.github.walker.taskcenter.service.AppRegService;
import com.github.walker.taskcenter.service.EventCtrService;
import com.github.walker.taskcenter.service.ScheduleCtrService;
import com.github.walker.taskcenter.vo.AppCallLog;
import com.github.walker.taskcenter.vo.AppReg;
import com.github.walker.taskcenter.vo.EventControl;
import com.github.walker.taskcenter.vo.ScheduleControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 应用注册及管理 Controller
 * <p/>
 * Created by HuQingmiao on 2015/5/20.
 */
@Controller
@RequestMapping(value = "appmgr")
public class AppRegMgrController extends BasicController {

    @Autowired
    private AppRegService appRegService;

    @Autowired
    private ScheduleCtrService scheduleCtrService;

    @Autowired
    private EventCtrService eventCtrService;

    @Autowired
    AppCallService appCallService;

    @RequestMapping(value = "query", method = RequestMethod.GET)
    public String queryApp(@ModelAttribute("form") AppRegForm form, Map<String, Object> map, HttpServletRequest request) {
        try {
            //HttpSession session = request.getSession();
            log.info(">>> queryApps()");
            String appName = form.getCriaAppName();
            String hostName = form.getCriaHostName();

            //分页查询, 1. 构造分页控制器
            PagerControl pager = new PagerControl(request, 10, 10);
            log.info("offset: " + pager.getOffset());

            //分页查询, 2.调用分页查询方法
            List<AppReg> appRegList = appRegService.findAppConfigs(null, appName, hostName,
                    pager.getOffset(), pager.getMaxRowcnt());

            //分页查询, 3.设置本次取到的记录数, 以及符合条件的总记录数
            PageList<AppReg> pageList = (PageList<AppReg>) appRegList;// 获得结果集条总数
            pager.setItemsCount(request, appRegList.size(), pageList.getTotalCount());

            map.put("appList", appRegList);
            return "listApps";

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            map.put("error", e.getMessage());
            return "listApps";

        }
    }

    @RequestMapping(value = "toAdd", method = RequestMethod.GET)
    public String toAdd(@ModelAttribute("form") AppRegForm form, Map<String, Object> map) {
        try {
            //HttpSession session = request.getSession();
            log.info(">>> queryApps()");
            String appName = form.getCriaAppName().trim();
            String hostName = form.getCriaHostName().trim();
            return "addApp";

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            map.put("error", e.getMessage());
            return "addApp";
        }
    }

    @RequestMapping(value = "addApp", method = RequestMethod.POST)
    public String addApp(@ModelAttribute("form") AppRegForm form, Map<String, Object> map) {
        try {
            log.info(">>> addApp()");
            String appCode = form.getAppCode();
            String appName = form.getAppName();

            AppReg existedAppReg = appRegService.findByCodeOrByName(appCode, appName);
            if (existedAppReg != null) {
                map.put("error", "提交失败, 请不要录入编码或名称重复的应用!");
                return "addApp";
            }

            AppReg appReg = new AppReg();
            PropertyUtil.copyProperties(form, appReg);

            appRegService.addAppConfig(appReg);
            map.put("success", "提交成功! ");
            return "addApp";

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            map.put("error", e.getMessage());
            return "addApp";
        }

    }


    @RequestMapping(value = "toUpdate", method = RequestMethod.GET)
    public String toUpdate(@ModelAttribute("form") AppRegForm form, Map<String, Object> map) {
        try {
            log.info(">>> toUpdate()");
            String id = form.getId();
            log.info("id: " + id);

            AppReg appReg = appRegService.findAppById(new Long(id));
            PropertyUtil.copyProperties(appReg, form);

            String appName = form.getCriaAppName().trim();
            String hostName = form.getCriaHostName().trim();
            return "updateApp";

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            map.put("error", e.getMessage());
            return "updateApp";
        }
    }

    @RequestMapping(value = "updateApp", method = RequestMethod.POST)
    public String updateApp(@ModelAttribute("form") AppRegForm form, Map<String, Object> map) {
        try {
            log.info(">>> updateApp()");
            String id = form.getId();
            String appCode = form.getAppCode();
            String appName = form.getAppName();

            AppReg existedAppReg = appRegService.findByCodeOrByName(appCode, appName);
            if (existedAppReg != null && !existedAppReg.getId().toString().equals(id)) {
                map.put("error", "修改失败, 请不要录入编码或名称重复的应用!");
                return "addApp";
            }

            AppReg appReg = appRegService.findAppById(new Long(id));
            PropertyUtil.copyProperties(form, appReg);

            appRegService.updateAppConfig(appReg);

            map.put("success", "修改成功! ");
            return "updateApp";

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            map.put("error", e.getMessage());
            return "updateApp";
        }
    }


    @RequestMapping(value = "delApp", method = RequestMethod.GET)
    public String delApp(@ModelAttribute("form") AppRegForm form, Map<String, Object> map, HttpServletRequest request) {
        try {
            log.info(">>> delApp()");
            String id = form.getId();
            log.info("id: " + id);

            appRegService.delAppConfig(new Long(id));

            return this.queryApp(form, map, request);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            map.put("error", e.getMessage());
            return "listApps";
        }
    }


    @RequestMapping(value = "toSetSchedule", method = RequestMethod.GET)
    public String toSetSchedule(@ModelAttribute("form") AppRegForm form, Map<String, Object> map) {
        try {
            log.info(">>> toSetSchedule()");
            String id = form.getId();
            log.info("id: " + id);

            ScheduleControl scheduleCtr = scheduleCtrService.findSchduleCtrByAppId(new Long(id));
            if (scheduleCtr != null) {
                int cronExp = Integer.parseInt(scheduleCtr.getCronExpression());

                if (cronExp % 86400 == 0) {
                    form.setTimeUnit("d");//天
                    form.setCronExpression(String.valueOf(cronExp / 86400));

                } else if (cronExp % 3600 == 0) {
                    form.setTimeUnit("h");//小时
                    form.setCronExpression(String.valueOf(cronExp / 3600));

                } else if (cronExp % 60 == 0) {
                    form.setTimeUnit("m");//分钟
                    form.setCronExpression(String.valueOf(cronExp / 60));

                } else {
                    form.setTimeUnit("s");//秒
                    form.setCronExpression(String.valueOf(cronExp));
                }
                form.setScheduleEnable(String.valueOf(scheduleCtr.getEnable()));

            } else {
                //设置默认的间隔时间: 10分钟
                form.setTimeUnit("m");//分钟
                form.setCronExpression("10");
                form.setScheduleEnable(String.valueOf(BasicService.ENABLE_TRUE));
            }

            return "setSchedule";

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            map.put("error", e.getMessage());
            return "setSchedule";
        }
    }


    @RequestMapping(value = "setSchedule", method = RequestMethod.POST)
    public String setSchedule(@ModelAttribute("form") AppRegForm form, Map<String, Object> map) {
        try {
            log.info(">>> setSchedule()");
            String id = form.getId();
            log.info("id: " + id);

            String enable = form.getScheduleEnable();//是否启用时间调度
            String timeUnit = form.getTimeUnit();
            int cronExp = Integer.parseInt(form.getCronExpression());
            if ("d".equals(timeUnit)) {
                cronExp *= 86400;

            } else if ("h".equals(timeUnit)) {
                cronExp *= 3600;

            } else if ("m".equals(timeUnit)) {
                cronExp *= 60;
            }

            ScheduleControl scheduleCtr = scheduleCtrService.findSchduleCtrByAppId(new Long(id));

            if (scheduleCtr != null) {
                scheduleCtr.setCronExpression(String.valueOf(cronExp));
                scheduleCtr.setEnable(new Integer(enable));
                scheduleCtr.setNextCallTime(null);//清空下一次的执行时间
                scheduleCtrService.updateScheduleCtr(scheduleCtr);
            } else {
                scheduleCtrService.addScheduleCtr(new Long(id), String.valueOf(cronExp));
            }

            map.put("success", "设置成功!");
            return "setSchedule";

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            map.put("error", e.getMessage());
            return "setSchedule";
        }
    }


    @RequestMapping(value = "toSetEvent", method = RequestMethod.GET)
    public String toSetEvent(@ModelAttribute("form") AppRegForm form, Map<String, Object> map) {
        try {
            log.info(">>> toSetEvent()");
            String id = form.getId();
            log.info("id: " + id);

            AppReg appReg = appRegService.findAppById(new Long(id));
            EventControl eventCtr = eventCtrService.findEventCtrByAppId(new Long(id));
            if (eventCtr != null) {
                form.setEventEnable(String.valueOf(eventCtr.getEnable()));
                form.setMqEventCode("start:" + appReg.getAppCode());
            } else {
                //设置默认的MQ消息码
                form.setMqEventCode("start:" + appReg.getAppCode());
                form.setEventEnable(String.valueOf(BasicService.ENABLE_TRUE));
            }

            return "setEvent";

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            map.put("error", e.getMessage());
            return "setEvent";
        }
    }


    @RequestMapping(value = "setEvent", method = RequestMethod.POST)
    public String setEvent(@ModelAttribute("form") AppRegForm form, Map<String, Object> map) {
        try {
            log.info(">>> setEvent()");
            String id = form.getId();
            log.info("id: " + id);

            String enable = form.getEventEnable();//是否启用事件调度

            EventControl eventCtr = eventCtrService.findEventCtrByAppId(new Long(id));

            if (eventCtr != null) {
                eventCtr.setEnable(new Integer(enable));
                eventCtrService.updateEventCtr(eventCtr);
            } else {
                eventCtrService.addEventCtr(new Long(id));
            }

            map.put("success", "设置成功!");
            return "setEvent";

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            map.put("error", e.getMessage());
            return "setEvent";
        }
    }


    @RequestMapping(value = "callNow", method = RequestMethod.GET)
    public String callNow(@ModelAttribute("form") AppRegForm form, Map<String, Object> map,HttpServletRequest request) {
        try {
            log.info(">>> toUpdate()");
            String id = form.getId();
            log.info("id: " + id);

            AppReg appReg = appRegService.findAppById(new Long(id));
            ICommand command = AbsCommnad.factory(appReg.getType());
            command.setApp(appReg);
            command.execute();

            map.put("success", "触发成功! 正在调用中...!");
            return this.queryApp(form,map,request);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            map.put("error", e.getMessage());
            return this.queryApp(form,map,request);
        }
    }

    @RequestMapping(value = "log/{appId}", method = RequestMethod.GET)
    public String queryLog(@PathVariable Long appId, @ModelAttribute("form") BasicForm form, Map<String, Object> map, HttpServletRequest request) {
        try {
            //HttpSession session = request.getSession();
            log.info(">>> queryLog()");
            request.setAttribute("appId",appId);

            //分页查询, 1. 构造分页控制器
            PagerControl pager = new PagerControl(request, 10, 10);
            //log.info("pageNum: " + pager.getPageNum());

            //分页查询, 2.调用分页查询方法
            List<AppCallLog> logList = appCallService.findCallLog(appId,
                    pager.getOffset(), pager.getMaxRowcnt());

            //分页查询, 3.设置本次取到的记录数, 以及符合条件的总记录数
            PageList<AppCallLog> pageList = (PageList<AppCallLog>) logList;// 获得结果集条总数
            pager.setItemsCount(request, logList.size(), pageList.getTotalCount());

            map.put("logList", logList);
            return "showCallLog";

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            map.put("error", e.getMessage());
            return "showCallLog";
        }
    }
}

