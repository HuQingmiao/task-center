package com.mucfc.taskcenter.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * PO 基类
 * <p/>
 * Created by HuQingmiao on 2015-5-13.
 */
public abstract class BasicVo implements Serializable {

    private static Logger log = LoggerFactory.getLogger(BasicVo.class);

    //存放当前实体类的属性及类型
    private HashMap<String, Class<?>> fieldNameTypeMap = new HashMap<String, Class<?>>(10);


    public BasicVo() {

        //将属性及其类型存入map(fieldName,fieldType)
        Field[] fields = this.getClass().getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            fieldNameTypeMap.put(fields[i].getName(), fields[i].getType());
        }
    }


    /**
     * 根据属性名取值
     *
     * @param propertyName 属性名
     * @return 属性值
     * @throws Exception
     */
    public Object get(String propertyName) throws Exception {

        String methodName = "get";
        if (propertyName.length() > 1 && Character.isUpperCase(propertyName.charAt(1))) {
            methodName += propertyName;
        } else {
            methodName += (Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1));
        }

        try {
            //builds the method name, such as: "getXX"
            Method method = this.getClass().getMethod(methodName,
                    new Class[]{});

            //Getting value of the field by the method.
            Object fieldValue = method.invoke(this, new Object[]{});

            return fieldValue;

        } catch (Exception e) {
            log.error("", e);
            throw e;
        }
    }


    /**
     * 对指定属性设置值
     *
     * @param propertyName
     * @param value
     * @throws Exception
     */
    public void set(String propertyName, Object value) throws Exception {

        String methodName = "set";
        if (propertyName.length() > 1 && Character.isUpperCase(propertyName.charAt(1))) {
            methodName += propertyName;
        } else {
            methodName += (Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1));
        }

        try {
            Method method = this.getClass().getMethod(methodName,
                    new Class[]{(Class<?>) fieldNameTypeMap.get(propertyName)});

            //Setting value of the field by the method.
            method.invoke(this, new Object[]{value});

        } catch (NoSuchMethodException e) {
            log.error("", e);
            throw e;
        }
    }


    public HashMap<String, Class<?>> fieldNameTypeMap() {
        return this.fieldNameTypeMap;
    }
}
