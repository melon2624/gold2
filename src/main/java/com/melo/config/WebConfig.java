package com.melo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zhangxin
 * @date 2025-05-05 21:43
 */
@Configuration
public class WebConfig  implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 假设你希望通过 /image/** 来访问图片
        registry.addResourceHandler("/image/**")
                .addResourceLocations("file:/home/zhangxin/upload/");
    }

}
