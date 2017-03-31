package com.jackycser.rocketmq.study.service;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.exception.ONSClientException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Jacky Zhang
 * @since 31/03/2017
 */
public class ProduceWithSpring {
    private static final String TOPIC = "finance_car_manage_topic_test";

    public static void main(String[] args) {
        /**
         * 生产者Bean配置在producer.xml中,可通过ApplicationContext获取或者直接注入到其他类(比如具体的Controller)中.
         */
        ApplicationContext context = new ClassPathXmlApplicationContext("producer.xml");
        System.out.println("context: " + context);
        System.out.println("ApplicationName: " + context.getApplicationName());

        Producer producer = (Producer) context.getBean("producer");
        //循环发送消息
        for (int i = 10; i < 15; i++) {
            Message msg = new Message( //
                // Message所属的Topic
                TOPIC,
                // Message Tag 可理解为Gmail中的标签，对消息进行再归类，方便Consumer指定过滤条件在MQ服务器过滤
                "TagA",
                // Message Body 可以是任何二进制形式的数据， MQ不做任何干预
                // 需要Producer与Consumer协商好一致的序列化和反序列化方式
                ("Hello MQ #" + i).getBytes());
            // 设置代表消息的业务关键属性，请尽可能全局唯一
            // 以方便您在无法正常收到消息情况下，可通过MQ 控制台查询消息并补发
            // 注意：不设置也不会影响消息正常收发
            msg.setKey("ORDER_ID-" + i);

            msg.setMsgID("myMsgId-" + i);
            // 发送消息，只要不抛异常就是成功
            try {
                SendResult sendResult = producer.send(msg);
                assert sendResult != null;
                System.out.println("send success: " + sendResult);
            } catch (ONSClientException e) {
                System.out.println("发送失败, errMsg: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
