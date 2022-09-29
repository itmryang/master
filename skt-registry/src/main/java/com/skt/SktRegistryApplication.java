package com.skt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SktRegistryApplication {
    public static void main(String[] args) {
        SpringApplication.run(SktRegistryApplication.class,args);
        System.out.println("http://127.0.0.1:10086/eureka");
    }
}

