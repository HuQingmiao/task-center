package com.github.walker.uloan.control;

import com.alibaba.fastjson.JSONObject;
import com.github.walker.common.BasicController;
import com.github.walker.uloan.control.vo.CreditApplyReq;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 接收从联通业务系统发来的订单
 * <p>
 * Created by huqingmiao on 2018/1/29.
 */
@Controller
@RequestMapping(value = "/creditApply", name = "订单接口")
public class CreditApplyCtl extends BasicController {

    /**
     * 招联调用本接口， 发送授信申请资料
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST, name = "导入订单")
    @ResponseBody
    public Map<String, Object> apply(@RequestBody CreditApplyReq creditApplyReq) throws Exception {
        log.info(">>creditApplyReq: " + JSONObject.toJSONString(creditApplyReq));
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //orderService.impOrderInfo(orderInfo, orderGoodsList,orderGoodsPropsList);

            return map;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            map.put("error", e.getMessage());
            return map;
        }
    }

}