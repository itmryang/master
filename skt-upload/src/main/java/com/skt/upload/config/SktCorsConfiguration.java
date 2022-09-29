package com.skt.upload.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class SktCorsConfiguration {
    @Bean
    public CorsFilter corsFilter(){
        //1.添加CORS配置信息
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 1)允许的域，不要写*，否则cookie无效
        corsConfiguration.addAllowedOrigin("http://manage.skt.com");
        //3)允许的请求方式
        corsConfiguration.addAllowedHeader("POST");
        corsConfiguration.addAllowedHeader("OPTIONS");
        //4)允许的头信息
        corsConfiguration.addAllowedMethod("*");
        //2.添加映射信息，拦截所有请求
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
        //3返回新的CorsFilter
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
