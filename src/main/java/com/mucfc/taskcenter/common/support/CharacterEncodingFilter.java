package com.mucfc.taskcenter.common.support;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 
 * 编码过滤，设置字符编码以解决中文乱码问题
 * 
 * @author HuQingmiao
 *  
 */
public class CharacterEncodingFilter implements Filter {

    protected FilterConfig filterConfig = null;

    protected String encoding = null;

    /**
     * 根据web.xml文件中的配置进行初始化
     */
    public void init(FilterConfig filterConfig) throws ServletException {

        this.filterConfig = filterConfig;
        this.encoding = filterConfig.getInitParameter("encoding");
    }

    /**
     * 设置编码
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        this.encoding = selectEncoding(request);
        if (encoding != null) {
            request.setCharacterEncoding(encoding);
        }
        chain.doFilter(request, response);
    }

    protected String selectEncoding(ServletRequest request) {
        return (this.encoding);
    }

    public void destroy() {

        this.encoding = null;
        this.filterConfig = null;

    }

}

