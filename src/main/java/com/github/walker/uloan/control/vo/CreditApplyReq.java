package com.github.walker.uloan.control.vo;

import com.github.walker.common.BasicVo;

/**
 * Created by huqingmiao on 2019-1-5.
 */
public class CreditApplyReq extends BasicVo {

    private String applyNo;

    private String mobileNo;

    private String custName;

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }
}
