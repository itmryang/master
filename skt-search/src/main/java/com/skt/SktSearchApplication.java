package com.skt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SktSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(SktSearchApplication.class, args);
    }
}