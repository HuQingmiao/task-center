package com.mucfc.taskcenter.common.mq;

import java.io.IOException;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * MQ生产者
 * <p/>
 * Created by HuQingmiao
 */
public class MqProducter {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private Connection connection = null;
    private Channel channel = null;

    //private String mqType = ConfigMgr.getProperty("rabbitmq.type");
    //private String mqExchangeName = ConfigMgr.getProperty("rabbitmq.queueName");

    /**
     * 向指定队列发送消息
     *
     * @param queueName
     * @param message
     * @throws Exception
     */
    public void send(String queueName, Object message) throws IOException {
        try {
            this.init();

            // 指定队列
            channel.queueDeclare(queueName, true, false, false, null);

            // 绑定队列到指定的交换机
            //channel.queueBind(queueName, mqExchangeName, queueName);
            //log.info("Bind queue {} to {} success! ", queueName, mqExchangeName);

            byte[] msgBodyBytes = message.toString().getBytes();

            // 往队列发送消息
            channel.basicPublish("", queueName,
                    MessageProperties.PERSISTENT_TEXT_PLAIN, msgBodyBytes);
            log.info("Send a message, it's length: {} bytes. ", msgBodyBytes.length);

        } finally {
            this.destory();
        }
    }


    private void init() throws IOException {
        try {
            connection = MqConnectFactory.getConnection();//获取连接
            log.info("MQ connected successful!");

            channel = connection.createChannel(); //创建频道
            log.info("Create channel successful!");

            // 指定交换机
            //channel.exchangeDeclare(mqExchangeName, mqType, true);

        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }


    private void destory() {
        try {
            if (null != channel) {
                channel.close();
            }
            if (null != connection) {
                connection.close();
            }
        } catch (IOException e) {
            log.error("Failed to close MQ connection: ", e);
        }
    }

}
