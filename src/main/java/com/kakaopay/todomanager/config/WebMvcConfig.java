package com.kakaopay.todomanager.config;

import com.kakaopay.todomanager.common.LogKeyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by jyp on 2018. 11. 24.
 * Email :ssm027@gmail.com
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer customize() {
        return p -> p.setOneIndexedParameters(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogKeyInterceptor());
    }
}
