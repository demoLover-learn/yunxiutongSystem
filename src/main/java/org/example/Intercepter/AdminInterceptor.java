package org.example.Intercepter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.context.BaseContext;
import org.example.properties.JwtProperties;
import org.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class AdminInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 校验jwt
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }
        //从请求头中获得令牌
        String token = request.getHeader("token");

        //校验令牌
       try{//parseJwt不只是解密，而是解密加自动校验一块完成的，如果不抛异常表示校验通过
           //抛异常表示有错误
           Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
           //获取当前用户id
           Long empId =((Number) claims.get("empId")).longValue();
           BaseContext.setCurrentId(empId);
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
