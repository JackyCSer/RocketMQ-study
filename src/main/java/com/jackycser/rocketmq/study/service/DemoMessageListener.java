package com.jackycser.rocketmq.study.service;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;

/**
 * @author Jacky Zhang
 * @since 31/03/2017
 */
public class DemoMessageListener implements MessageListener {
    public Action consume(Message message, ConsumeContext context) {
        System.out.println("Message: " + message);
        System.out.println("Message content: " + new String(message.getBody()));
        System.out.println("ConsumeContext: " + context);
        try {
            //do something..
            return Action.CommitMessage;
        } catch (Exception e) {
            //消费失败
            return Action.ReconsumeLater;
        }
    }
}
