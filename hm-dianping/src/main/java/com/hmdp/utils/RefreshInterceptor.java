package com.hmdp.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmdp.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.hmdp.utils.RedisConstants.LOGIN_USER_KEY;
import static com.hmdp.utils.RedisConstants.LOGIN_USER_TTL;

public class VerifyInterceptor implements HandlerInterceptor {

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HttpSession session = request.getSession();
//
//        UserDTO user = (UserDTO) session.getAttribute("user");
//        if(user == null){
//            response.setStatus(401);
//            return false;
//        }
//
//        UserHolder.saveUser(user);
//
//        return true;
//    }

    private final StringRedisTemplate stringRedisTemplate;

    public VerifyInterceptor(StringRedisTemplate template){
        stringRedisTemplate = template;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("authorization");
        if(StrUtil.isBlank(authorization)){
            response.setStatus(401);
            return false;
        }

        String toKey = LOGIN_USER_KEY + authorization;

        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(toKey);
        if(entries.isEmpty()){
            response.setStatus(401);
            return false;
        }

        UserDTO userDTO = BeanUtil.fillBeanWithMap(entries, new UserDTO(), false);
        UserHolder.saveUser(userDTO);

        stringRedisTemplate.expire(toKey, LOGIN_USER_TTL, TimeUnit.SECONDS);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }
}
