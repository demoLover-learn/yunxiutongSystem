package org.example.configration;

import jakarta.annotation.Resource;
import org.example.Intercepter.AdminInterceptor;
import org.example.Intercepter.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
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

}
