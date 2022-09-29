package com.skt.item.message;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragelyByCircle;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueByConfig;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RocketMQMessageListener(consumerGroup = "JXB_ORDER_GROUP", topic = "test-tx-rocketmq")
public class TestConsumer implements RocketMQListener<Object>, RocketMQPushConsumerLifecycleListener {

    @Override
    public void onMessage(Object message) {
        System.out.println("test-tx-rocketmq message : " + message);
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {

    }
}

