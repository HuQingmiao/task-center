package com.github.walker.common.support;
import java.util.HashMap;

/**
 * 通用返回结果对象
 *
 * @author luoguohui
 *         Created 2017/4/17 10:51
 */
public class ApiResultEntity {

    // 返回码KEY,  0：成功 1：失败
    public static final String RETURN_CODE_SUCC = "0";

    // 返回码KEY,  0：成功 1：失败
    public static final String RETURN_CODE_FAIL = "1";


//    public static final int SUCCESS = 200;
//    public static final int FAIL = 400;
//    public static final int NOAUTH = 401;


    private String ret;

    private HashMap<String, Object> data;

    private String errCode;

    private String errMsg;


    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

}
