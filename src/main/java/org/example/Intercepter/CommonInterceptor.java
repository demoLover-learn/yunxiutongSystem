package org.example.Intercepter;

import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.context.BaseContext;
import org.example.properties.JwtProperties;
import org.example.util.JwtUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.TimeUnit;
@Component
public class CommonInterceptor implements HandlerInterceptor {
    @Resource
    private JwtProperties jwtProperties;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) return true;
        String token = request.getHeader("token");
        // 依次尝试用户/工人/管理员三种身份，任一校验通过即放行
        try {
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            Long userId = ((Number) claims.get("userId")).longValue();
            String redisToken = stringRedisTemplate.opsForValue().get("login:user:" + userId);
            if (redisToken != null && redisToken.equals(token)) {
                stringRedisTemplate.expire("login:user:" + userId, 2, TimeUnit.HOURS);
                BaseContext.setCurrentId(userId);
                return true;
            }
        } catch (Exception ignored) {}
        try {
            Claims claims = JwtUtil.parseJWT(jwtProperties.getWorkerSecretKey(), token);
            Long workerId = ((Number) claims.get("workerId")).longValue();
            String redisToken = stringRedisTemplate.opsForValue().get("login:worker:" + workerId);
            if (redisToken != null && redisToken.equals(token)) {
                stringRedisTemplate.expire("login:worker:" + workerId, 2, TimeUnit.HOURS);
                BaseContext.setCurrentId(workerId);
                return true;
            }
        } catch (Exception ignored) {}
        try {
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
            Long empId = ((Number) claims.get("empId")).longValue();
            String redisToken = stringRedisTemplate.opsForValue().get("login:admin:" + empId);
            if (redisToken != null && redisToken.equals(token)) {
                stringRedisTemplate.expire("login:admin:" + empId, 2, TimeUnit.HOURS);
                BaseContext.setCurrentId(empId);
                return true;
            }
        } catch (Exception ignored) {}
        response.setStatus(401);
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception
            ex) {
        BaseContext.removeCurrentId();
    }
}
