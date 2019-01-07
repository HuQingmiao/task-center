package com.github.walker.uloan.control;

import com.alibaba.fastjson.JSONObject;
import com.github.walker.uloan.control.vo.CreditApplyReq;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;


/**
 * Created by huqingmiao on 2019-1-5.
 */

public class CreditApplyCtlTest {
    static String url="http://localhost:8080/uloan/creditApply/submit";

    public static void main(String[] args) throws Exception {

        String result = null;

        try {
            //设置请求头
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json");

            //设置请求体
            CreditApplyReq reqDto = new CreditApplyReq();
            reqDto.setApplyNo("asdfasfa");
            reqDto.setCustName("Laoli");

            StringEntity entity = new StringEntity(JSONObject.toJSONString(reqDto));
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            post.setEntity(entity);

            //获取返回信息
            HttpClient client = HttpClients.createDefault();
            HttpResponse response = client.execute(post);
            result = response.toString();
            System.out.println("<<<"+result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}