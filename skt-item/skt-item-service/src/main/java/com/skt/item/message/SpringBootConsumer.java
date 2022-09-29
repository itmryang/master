package com.skt.item.message;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragelyByCircle;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = "CNJING", topic = "zhangjing")
public class SpringBootConsumer implements RocketMQListener<Object> , RocketMQPushConsumerLifecycleListener {

    @Override
    public void onMessage(Object message) {
        System.out.println("zhangjing message : "+ message);
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {

        consumer.setAllocateMessageQueueStrategy(new AllocateMessageQueueAveragelyByCircle());
    }
}
