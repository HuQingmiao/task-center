package com.github.walker.common.support;


public final class ExceptionConstant {

    // 异常码KEY
    public static final String ERROE_CODE = "errCode";

    // 异常信息KEY
    public static final String ERROE_MSG = "errMsg";

    // 未知的异常
    public static final String ERROE_CODE_UNKNOW = "00000";


    // 返回码KEY,  0：成功 1：失败 其他待扩展
    public static final String RETURN_CODE = "ret";

    // 返回数据KEY
    public static final String RETURN_DATA = "data";


    // 返回码-成功
    public static final String RETURN_CODE_SUCCESS = "0";

    // 返回码-失败
    public static final String RETURN_CODE_FAIL = "1";




//    // 请求流水号
//    public static final String REQUEST_NO = "requestNo";
//
//    // 长会话key
//    public static final String LONG_SESSION_KEY = "____LONG_SESSION_TIMEOUT____";
//
//    // 会话id加密
//    public static final String SESSION_KEY = "SESSION_KEY";
//
    // request异常KEY
    public static final String REQUEST_EXCEPTION_KEY = "REQUEST_EXCEPTION_KEY";

    /**
     * 不可实例化
     */
    private ExceptionConstant() {
    }

}
