package com.mucfc.taskcenter.common.mq;

import java.io.IOException;

import com.mucfc.taskcenter.common.support.PropertyHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by huqingmiao on 2015-4-22.
 */
public class MqConnectFactory {

    private static final Logger log = LoggerFactory.getLogger(MqConnectFactory.class);

    private static final ConnectionFactory factory = new ConnectionFactory();

    static {
        factory.setHost(PropertyHolder.getProperty("rabbitmq.host"));
        factory.setPort(Integer.parseInt(PropertyHolder.getProperty("rabbitmq.port")));
        factory.setUsername(PropertyHolder.getProperty("rabbitmq.username"));
        factory.setPassword(PropertyHolder.getProperty("rabbitmq.password"));
    }

    public static Connection getConnection() throws IOException {
        log.info("mq host: {}:{} ", PropertyHolder.getProperty("rabbitmq.host"), PropertyHolder.getProperty("rabbitmq.port"));
        return factory.newConnection();
    }
}