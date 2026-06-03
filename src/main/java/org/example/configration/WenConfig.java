package org.example.configration;

import jakarta.annotation.Resource;
import org.example.Intercepter.AdminInterceptor;
import org.example.Intercepter.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WenConfig implements WebMvcConfigurer {

    @Resource
    private AdminInterceptor adminInterceptor;
    @Resource
    private UserInterceptor userInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //管理员后台拦截设置
        registry.addInterceptor(adminInterceptor)
                //拦截所有后台接口做接口校验
                .addPathPatterns("/api/admin/**")
                //放行admin登录接口
                .excludePathPatterns("/api/admin/auth/login");
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/api/user/**")
        .excludePathPatterns("/api/user/login","/api/user/register");
    }

    //加载图片的静态资源映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //"/uploads/**",文件上返回的usl拼接什么虚拟地址,这里就用什么
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:E:/AAADeepSeek/image/");
    }
}
