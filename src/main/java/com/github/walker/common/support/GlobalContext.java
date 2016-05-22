package com.github.walker.common.support;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 设计专门的类提供共享的容器环境，以免在多次对Spring.xml进行加载
 * <p/>
 * Created by Huqingmiao on 2015-5-14.
 */
public class GlobalContext {

    public static ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

}
