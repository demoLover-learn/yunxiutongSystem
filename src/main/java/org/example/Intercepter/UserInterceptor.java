package org.example.Intercepter;

import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.context.BaseContext;
import org.example.properties.JwtProperties;
import org.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.TimeUnit;

@Component
public class UserInterceptor implements HandlerInterceptor {

    @Resource
    private JwtProperties jwtProperties;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前拦截到的是Controller的方法还是其他资源
        //静态资源直接放行
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

       //1.获取请求头
        String token = request.getHeader("token");
        //try抛异常直接报错误
        try{ //解密token//String secretKey, String token
            String secretKey = jwtProperties.getUserSecretKey();

            Claims claims = JwtUtil.parseJWT(secretKey, token);
            Long userId = ((Number) claims.get("userId")).longValue();
            String redisToken = stringRedisTemplate.opsForValue().get("login:user:" + userId);

            if (redisToken == null || !redisToken.equals(token)) {
                throw new RuntimeException("登录已失效，请重新登录");
            }
            stringRedisTemplate.expire("login:user:" + userId, 2, TimeUnit.HOURS);
            //放进抽屉
            BaseContext.setCurrentId(userId);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            response.setStatus(401);
            return false;
        }


    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContext.removeCurrentId();
    }
}
