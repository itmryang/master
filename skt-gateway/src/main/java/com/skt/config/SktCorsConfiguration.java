package com.skt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
@Configuration
public class SktCorsConfiguration {
    @Bean
    public CorsFilter corsFilter(){
        //1.添加cors配置信息
        CorsConfiguration config = new CorsConfiguration();
        //1)允许的域，写*则cookie无法使用
        config.addAllowedOrigin("http://manage.skt.com");
        config.addAllowedOrigin("http://www.skt.com");
        //2)是否发送cookie信息
        config.setAllowCredentials(true);
        //3)允许的请求方式
        config.addAllowedMethod("*");
        //4)允许的头信息
        config.addAllowedHeader("*");
        //2.添加映射路径，拦截所有请求
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**",config);
        //3.返回新的corsFilter
        return new CorsFilter(configurationSource);
    }
}
