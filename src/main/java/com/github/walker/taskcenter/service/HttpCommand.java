package com.github.walker.taskcenter.service;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.github.walker.common.AbsCommnad;

/**
 * Http Request命令类
 * <p/>
 * Created by HuQingmiao on 2015-5-14.
 */

@Service
@Scope("prototype")
public class HttpCommand extends AbsCommnad {

    protected static Logger log = LoggerFactory.getLogger(HttpCommand.class);

    @Override
    public void execute() {
        try {
            // http请求
            String commandLine = this.getApp().getCommand();
            // post请求,失败抛出运行时异常
            post(commandLine);
            //调度后做相应记录
            this.doAfterCall(this.getApp());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 发送post请求
     *
     * @throws Exception
     */
    private static void post(String strUrl) throws Exception {
        PrintWriter out = null;
        BufferedReader in = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("Charset", "UTF-8");

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(6000);
            conn.setUseCaches(false);

            // 请求失败抛出运行时异常
            if (!(conn.getResponseCode() == HttpURLConnection.HTTP_OK)) {
                throw new RuntimeException("http post failt. url={" + strUrl + "},code={" + conn.getResponseCode()
                        + "}");
            }
        } finally {
            try {
                if (conn != null) {
                    conn.disconnect();
                }
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
    }

}
