package com.skt.item.message;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragelyByCircle;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueByConfig;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RocketMQMessageListener(consumerGroup = "YNJN", topic = "yangjian123")
public class TestConsumer1 implements RocketMQListener<Object>, RocketMQPushConsumerLifecycleListener {

    @Override
    public void onMessage(Object message) {
        System.out.println("yangjian123 message : " + message);
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {

        AllocateMessageQueueByConfig config = new AllocateMessageQueueByConfig();
        config.setMessageQueueList(Arrays.asList(new MessageQueue("yangjian123","yangjiandeMacBook-Pro.local",1)));
        consumer.setAllocateMessageQueueStrategy(config);
    }
}

