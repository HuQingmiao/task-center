package com.github.walker.common.constant;

public enum Binary {

    NO(0, "否/无效/禁用"),

    YES(1, "是/有效/启用");

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    private String value;

    private Binary(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}


