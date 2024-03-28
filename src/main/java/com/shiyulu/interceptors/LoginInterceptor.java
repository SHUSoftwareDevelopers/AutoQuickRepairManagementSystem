package com.shiyulu.interceptors;

import com.shiyulu.anno.RequireRole;
import com.shiyulu.utils.JwtUtil;
import com.shiyulu.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.Map;

//拦截器
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        Map<String, Object> claims;

        //令牌验证
        String token = request.getHeader("Authorization");
        //验证token
        try {
            //从redis中获取相同的token
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            String redisToken = operations.get(token);
            if(redisToken==null){
                //token已经失效
                throw new RuntimeException();
            }

            claims = JwtUtil.parseToken(token);

        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }

        //验证@RequireRole中的身份要求是否满足
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            RequireRole requireRole = method.getMethodAnnotation(RequireRole.class);

            if (requireRole != null) {
                // 获取当前用户的角色
                String currentUserRole = claims.get("usertype").toString();

                String[] requireRoles = requireRole.value();
                boolean hasRequiredRole = Arrays.asList(requireRoles).contains(currentUserRole);
                // 检查用户是否有所需的角色
                if (!hasRequiredRole) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                    return false;
                }
            }
        }

        //把业务数据存储到ThreadLocal中
        ThreadLocalUtil.set(claims);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空ThreadLocal中的数据
        ThreadLocalUtil.remove();
    }
}
