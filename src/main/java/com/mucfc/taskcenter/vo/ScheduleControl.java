package com.mucfc.taskcenter.vo;

import com.mucfc.taskcenter.common.BasicVo;

import java.sql.Timestamp;

/**
 * 时间调度控制
 */
public class ScheduleControl extends BasicVo {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long appId;
    private String cronExpression;
    private Timestamp nextCallTime;

    private Integer enable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Timestamp getNextCallTime() {
        return nextCallTime;
    }

    public void setNextCallTime(Timestamp nextCallTime) {
        this.nextCallTime = nextCallTime;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }
}
