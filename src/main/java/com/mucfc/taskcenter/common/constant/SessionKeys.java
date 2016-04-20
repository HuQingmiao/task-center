package com.mucfc.taskcenter.common.constant;

public enum SessionKeys {

    //    CURR_PAGENUM("CURR_PAGENUM", "当前页吗"),
//
    CURR_ITMCNT("A", "当前记录条数");


    private String code;
    private String value;

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    private SessionKeys(String code, String value) {
        this.code = code;
        this.value = value;
    }
}
