package com.github.walker.taskcenter.vo;

import com.github.walker.common.BasicVo;

public class AppCallLog extends BasicVo {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long appId;
    private String appCode;
    private String action;
    private java.sql.Timestamp time;
    private Integer result;
    private String exception;


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

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public java.sql.Timestamp getTime() {
        return time;
    }

    public void setTime(java.sql.Timestamp time) {
        this.time = time;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

}

/*�����ֶ�������:
"id", "app_id", "app_code", "action", "time", "result", "exception"
*/