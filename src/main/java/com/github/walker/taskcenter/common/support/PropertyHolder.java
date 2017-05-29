package com.github.walker.taskcenter.common.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 对Spring的properties文件加装器进行扩展，提供获取属性的静态方法
 * <p/>
 * Created by HuQingmiao on 2015-8-24.
 */
public class PropertyHolder extends PropertyPlaceholderConfigurer {

    private static Map<String, String> ctxPropertiesMap = new HashMap<String, String>();


    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory,
                                     Properties props) throws BeansException {
        super.processProperties(beanFactory, props);
        //load properties to ctxPropertiesMap
        for (Object key : props.keySet()) {
            String keyStr = (String) key;
            String value = props.getProperty(keyStr);
            ctxPropertiesMap.put(keyStr, value);
        }
    }

    public static String getProperty(String name) {
        return ctxPropertiesMap.get(name);
    }
}