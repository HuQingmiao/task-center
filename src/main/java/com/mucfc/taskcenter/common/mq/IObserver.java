package com.mucfc.taskcenter.common.mq;

/**
 *
 * 观察者接口。 若MQ客户端接收到消息，会通知实现本接口的观察者对象
 *
 * Created by huqingmiao on 2015-4-22.
 */
public interface IObserver {

    public void update(String msg);
}
