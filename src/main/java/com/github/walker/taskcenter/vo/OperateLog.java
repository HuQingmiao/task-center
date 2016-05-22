package com.github.walker.taskcenter.vo;

import com.github.walker.common.BasicVo;

public class OperateLog extends BasicVo {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String opTable;
    private String opDataDesc;
    private String updateContent;
    private String opUser;
    private java.sql.Date opTime;


    public Long getId() {
        return id;
   }

    public void setId(Long id) {
        this.id = id;
   }

    public String getOpTable() {
        return opTable;
   }

    public void setOpTable(String opTable) {
        this.opTable = opTable;
   }

    public String getOpDataDesc() {
        return opDataDesc;
   }

    public void setOpDataDesc(String opDataDesc) {
        this.opDataDesc = opDataDesc;
   }

    public String getUpdateContent() {
        return updateContent;
   }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
   }

    public String getOpUser() {
        return opUser;
   }

    public void setOpUser(String opUser) {
        this.opUser = opUser;
   }

    public java.sql.Date getOpTime() {
        return opTime;
   }

    public void setOpTime(java.sql.Date opTime) {
        this.opTime = opTime;
   }

}

/*�����ֶ�������:
"id", "op_table", "op_data_desc", "update_content", "op_user", "op_time"
*/