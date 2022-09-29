package com.skt.controller;

import com.alibaba.fastjson.JSONObject;
import com.skt.Api.GoodsApi;
import com.skt.dto.UserInfo;
import com.skt.mapper.UserInfoMapper;
import com.skt.pojo.Brand;
import com.skt.pojo.SpuDetail;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import skt.common.pojo.PageResult;

import java.util.Random;

@RestController
@RequestMapping("query/all")
public class GoodsController {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/remote")
    public ResponseEntity<PageResult<Brand>> queryBrandsByPage() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        DefaultMQProducer producer = rocketMQTemplate.getProducer();
        org.apache.rocketmq.common.message.Message message = new org.apache.rocketmq.common.message.Message();

        producer.setProducerGroup("YNJN");
        MessageQueue messageQueue = new MessageQueue();
        message.setTopic("yangjian123");
        messageQueue.setTopic("yangjian123");
        messageQueue.setBrokerName("yangjiandeMacBook-Pro.local");
        message.setBody("yangjian".getBytes());
        messageQueue.setQueueId(1);
        producer.send(message,messageQueue,1000L);
        rocketMQTemplate.syncSend("yangjian","hello");
        int i = new Random().nextInt(1000);
        String name = "name" + i;
        UserInfo userInfo = new UserInfo();
        userInfo.setGender("1");
        userInfo.setUid(name);
        DefaultMQProducer producer1 = rocketMQTemplate.getProducer();
        producer1.setProducerGroup("CNJING");
        org.apache.rocketmq.common.message.Message message1 = new org.apache.rocketmq.common.message.Message();
        message1.setTopic("zhangjing");
        message1.setBody("yangjian".getBytes());
        producer1.send(message1,1000L);

        String s = JSONObject.toJSONString(userInfo);
        Message<UserInfo> msg = MessageBuilder.withPayload(userInfo).setHeader("key", s).build();
        TransactionSendResult sendResult = rocketMQTemplate.sendMessageInTransaction("test-tx-rocketmq", msg, "托尔斯泰");
        System.out.println(sendResult);
        return ResponseEntity.ok(null);
    }
}
