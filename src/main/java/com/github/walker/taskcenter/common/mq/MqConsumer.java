package com.github.walker.taskcenter.common.mq;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

/**
 * MQ消费者，接收MQ消息后调用相应观察者，由观察者调用具体的业务逻辑
 * <p/>
 * Created by HuQingmiao
 */
public class MqConsumer  {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private Connection connection = null;
    private Channel channel = null;


    //private String mqType = ConfigMgr.getProperty("rabbitmq.type");
    //private String mqExchangeName = ConfigMgr.getProperty("rabbitmq.queueName");

    /**
     * 从指定队列接收消息，然后通过观察者对象处理
     *
     * @param queueName
     * @param observer
     * @throws Exception
     */
    public void receive(String queueName, IObserver observer) throws Exception {
        try {
            this.init();

            // 指定队列
            channel.queueDeclare(queueName, true, false, false, null);

            // 绑定队列到指定的交换机
            //channel.queueBind(queueName, mqExchangeName, queueName);
            //log.info("Bind queue {} to {} success! ", queueName, mqExchangeName);

            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.basicConsume(queueName, true, consumer);

            while (true) {
                //阻塞，直到接收到一条消息
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String message = new String(delivery.getBody());
                log.info("Received a message: " + message);

                observer.update(message);
            }

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

//    /**
//     * 重新发送处理出错的信息
//     *
//     * @param queuename
//     * @param message
//     * @return void
//     * @throws java.io.IOException
//     */
//    private void resend(String queuename, String message) throws IOException {
//        MqProducter prd = new MqProducter();
//        prd.send(queuename, message);
//    }

}
