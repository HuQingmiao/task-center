package com.github.walker.taskcenter.vo;

import com.github.walker.taskcenter.common.BasicVo;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 应用注册
 */
public class AppReg extends BasicVo {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String appName;
    private String appCode;
    private String hostName;
    private Integer type;
    private String command;
    private Integer state;
    private String createUser;
    private Timestamp createTime;

    private Long scheduleId;

    private Long eventId;

    private String cronExpression;

    private Date nextCallTime;

    private Integer scheduleEnable;

    private Integer eventEnable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }


    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }


    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Date getNextCallTime() {
        return nextCallTime;
    }

    public void setNextCallTime(Date nextCallTime) {
        this.nextCallTime = nextCallTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }


    public Integer getScheduleEnable() {
        return scheduleEnable;
    }

    public void setScheduleEnable(Integer scheduleEnable) {
        this.scheduleEnable = scheduleEnable;
    }

    public Integer getEventEnable() {
        return eventEnable;
    }

    public void setEventEnable(Integer eventEnable) {
        this.eventEnable = eventEnable;
    }
}
