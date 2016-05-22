package com.github.walker.taskcenter.control;

import com.github.walker.common.BasicForm;

/**
 * Created by HuQingmiao on 2015/5/18
 */
public class AppRegForm extends BasicForm {

    private String id;

    private String appCode;

    private String appName;

    private String hostName;

    private String command;

    private String type;


    private String timeUnit;//时分秒

    private String cronExpression;

    private String mqEventCode;

    private String scheduleEnable;

    private String eventEnable;


    private String criaAppName;

    private String criaHostName;

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getHostName() {
        return hostName;
    }

    public String getCriaAppName() {
        return criaAppName;
    }

    public void setCriaAppName(String criaAppName) {
        this.criaAppName = criaAppName;
    }

    public String getCriaHostName() {
        return criaHostName;
    }

    public void setCriaHostName(String criaHostName) {
        this.criaHostName = criaHostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }


    public String getScheduleEnable() {
        return scheduleEnable;
    }

    public void setScheduleEnable(String scheduleEnable) {
        this.scheduleEnable = scheduleEnable;
    }

    public String getEventEnable() {
        return eventEnable;
    }

    public void setEventEnable(String eventEnable) {
        this.eventEnable = eventEnable;
    }

    public String getMqEventCode() {
        return mqEventCode;
    }

    public void setMqEventCode(String mqEventCode) {
        this.mqEventCode = mqEventCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }
}


