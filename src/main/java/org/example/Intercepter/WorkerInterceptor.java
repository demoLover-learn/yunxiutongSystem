package org.example.Intercepter;

import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.context.BaseContext;
import org.example.properties.JwtProperties;
import org.example.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class WorkerInterceptor implements HandlerInterceptor {
    @Resource
    private JwtProperties jwtProperties;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      //判断传过来的是不是方法类型的参数，不是的话直接放行、
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        //获取Token
        String token = request.getHeader("token");
        //解析token
        try{
            Claims claims = JwtUtil.parseJWT(jwtProperties.getWorkerSecretKey(), token);
            Long userId = ((Number)claims.get("userId")).longValue();
            BaseContext.setCurrentId(userId);
            return true;
        }catch (Exception e){
        e.printStackTrace();
        response.setStatus(401);
        return false;}
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContext.removeCurrentId();
    }
}
