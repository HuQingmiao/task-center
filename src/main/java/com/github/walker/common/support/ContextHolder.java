package com.github.walker.common.support;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 用于从Spring Context中显示地获取Bean.
 */
public class ContextHolder implements ApplicationContextAware, DisposableBean {
    private static ApplicationContext applicationContext = null;

    public ContextHolder() {
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void destroy() throws Exception {
        applicationContext = null;
    }

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ContextHolder.applicationContext = applicationContext;
    }


    public static <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class className) {
        return (T) applicationContext.getBean(className);
    }
}
