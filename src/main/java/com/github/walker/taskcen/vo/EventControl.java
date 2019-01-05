package com.github.walker.taskcen.vo;


import com.github.walker.common.BasicVo;

/**
 * 事件调度控制
 */
public class EventControl extends BasicVo {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long appId;  //应用程序ID

    private Integer enable;

    public Long getId() {
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public Long getAppId() {
        return appId;
    }
    public void setAppId(Long appId){
        this.appId = appId;
    }


    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }
}
