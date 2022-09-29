package com.skt.listenner;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.skt.dto.UserInfo;
import com.skt.mapper.UserInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
//@RocketMQTransactionListener表明这个一个生产端的消息监听器，需要配置监听的事务消息生产者组。
// 实现RocketMQLocalTransactionListener接口，重写执行本地事务的方法和检查本地事务方法
@RocketMQTransactionListener
public class TxProducerListener implements RocketMQLocalTransactionListener {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private UserInfoMapper userInfoMapper;
 
 
    /**
     * 每次推送消息会执行executeLocalTransaction方法，首先会发送半消息，到这里的时候是执行具体本地业务，
     * 执行成功后手动返回RocketMQLocalTransactionState.COMMIT状态，
     * 这里是保证本地事务执行成功，如果本地事务执行失败则可以返回ROLLBACK进行消息回滚。 此时消息只是被保存到broker，并没有发送到topic中，broker会根据本地返回的状态来决定消息的处理方式。
     * @param msg
     * @param arg
     * @return
     */

    @Override
    @Transactional
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {

        log.info("开始执行本地事务");
        MessageHeaders headers = msg.getHeaders();
        Object lo = headers.get("key");
        String s = lo.toString();
        JSONObject sk = JSONObject.parseObject(s);
        UserInfo userInfo = sk.toJavaObject(UserInfo.class);

        userInfoMapper.insertUserInfo(userInfo);
        int a=1/0;

            //这个地方抛出异常，消息状态会是UNKNOWN状态
        log.info("本地事务提交");
        return RocketMQLocalTransactionState.COMMIT;
    }
 
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        log.info("开始执行回查");
        List<UserInfo> select = new ArrayList<>();
        if (CollectionUtils.isEmpty(select)) {
            log.info("回滚半消息");
            return RocketMQLocalTransactionState.ROLLBACK;
        }
        log.info("提交半消息");
        return RocketMQLocalTransactionState.COMMIT;
    }
}