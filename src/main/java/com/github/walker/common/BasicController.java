package com.github.walker.common;

import com.github.walker.common.support.ApiResultEntity;
import com.github.walker.common.support.ExceptionConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * MVC Controller基类，可在此提供Controller层的公共方法，如果上传、下载、参数转换等
 * <p/>
 * Created by Huqingmiao on 2015-5-16.
 */
public abstract class BasicController {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取成功返回实体
     *
     * @return
     */
    protected ApiResultEntity getSuccessEntity() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("success", "处理成功!");

        ApiResultEntity apiResultEntity = new ApiResultEntity();
        apiResultEntity.setRet(ApiResultEntity.RETURN_CODE_SUCC);
        apiResultEntity.setData(data);
        apiResultEntity.setErrCode("");
        apiResultEntity.setErrMsg("");
        return apiResultEntity;
    }

    /**
     * 获取成功返回实体
     *
     * @param data
     * @return
     */
    protected ApiResultEntity getSuccessEntity(HashMap<String, Object> data) {
        if (log.isDebugEnabled()) {
            log.debug("请求成功处理返回数据:" + data);
        }
        if (data == null) {
            data = new HashMap(0);
        }
        ApiResultEntity apiResultEntity = new ApiResultEntity();
        apiResultEntity.setRet(ApiResultEntity.RETURN_CODE_SUCC);
        apiResultEntity.setData(data);
        apiResultEntity.setErrCode("");
        apiResultEntity.setErrMsg("");
        return apiResultEntity;
    }

    protected ApiResultEntity getErrorEntity(String errMsg) {
        ApiResultEntity apiResultEntity = new ApiResultEntity();
        apiResultEntity.setRet(ApiResultEntity.RETURN_CODE_FAIL);
        apiResultEntity.setData(new HashMap(0));
        apiResultEntity.setErrCode(ExceptionConstant.ERROE_CODE_UNKNOW);
        apiResultEntity.setErrMsg(errMsg);
        return apiResultEntity;
    }


//    protected ApiResultEntity getErrorEntity(Exception ex) {
//
//        ApiResultEntity apiResultEntity = new ApiResultEntity();
//        apiResultEntity.setRet(ApiResultEntity.RETURN_CODE_FAIL);
//        apiResultEntity.setData(new HashMap(0));
//
//        if (ex instanceof BusinessException) {
//            BusinessException sex = (BusinessException) ex;
//            apiResultEntity.setErrCode(String.valueOf(sex.getErrorCode()));
//            apiResultEntity.setErrCode(sex.getMessage());
//            log.warn("业务异常:", ex);
//        }
//
//        else if (ex instanceof SystemException) {
//            SystemException sex = (SystemException) ex;
//            apiResultEntity.setErrCode(String.valueOf(sex.getErrorCode()));
//            apiResultEntity.setErrMsg(sex.getMessage());
//            log.warn("系统异常:", ex);
//        }
//
//        else {
//            apiResultEntity.setErrCode(ExceptionConstant.ERROE_CODE_UNKNOW);
//            apiResultEntity.setErrMsg(ex.toString());
//        }
//
//        return apiResultEntity;
//    }

}
