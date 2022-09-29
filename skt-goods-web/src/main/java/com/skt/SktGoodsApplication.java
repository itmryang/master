package com.skt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.skt"})
@EnableDiscoveryClient
@MapperScan("com.skt.mapper")
public class SktGoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(SktGoodsApplication.class, args);}
}
