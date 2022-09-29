package com.skt.item.utils;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.unit.DataUnit;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * redis锁 调用lock锁住资源，方法执行完后要调用unlock释放锁
 * Created by Roney on 2019/3/21 17:19
 *
 * @author Roney
 */
@SuppressWarnings("ALL")
@Component
@Slf4j
public class RedisLockUtils {

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * //锁前缀
     */
    private String prefix = "project:insurance:";


    public Boolean lock(String key) {
        while (!getLock(key)) {
            try {
                // 获取不到锁等待200毫秒
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return true;
    }

    private Boolean getLock(String key) {
        key = prefix + key;
        // 获取锁
        if (redisTemplate.boundValueOps(key).setIfAbsent(new Date())) {
            Boolean aBoolean = redisTemplate.boundValueOps(key).setIfAbsent(new Date());

            log.info("===============lock key=" + key);
            return true;
        } else {
            // 未获取锁，检查是否死锁
            Date oldKeyTime = (Date) redisTemplate.opsForValue().get(key);
            if (oldKeyTime != null) {
                LocalDateTime oldKey = DateUtil.toLocalDateTime(oldKeyTime);;
                if (LocalDateTime.now().isAfter(oldKey.plusSeconds(10))) {
                    if (redisTemplate.boundValueOps(key + ":timeOut").setIfAbsent(
                            new Date())) {
                        // 清除死锁
                        redisTemplate.delete(key);
                        redisTemplate.boundValueOps(key).setIfAbsent(new Date());
                        redisTemplate.expire(key + ":timeOut", 5, TimeUnit.SECONDS);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void unlock(String key) {
        key = prefix + key;
        try {
            log.info("===============unlock key=" + key);
            redisTemplate.delete(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
