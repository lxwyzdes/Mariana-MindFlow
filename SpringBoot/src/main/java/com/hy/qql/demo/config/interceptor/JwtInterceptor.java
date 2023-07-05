package com.hy.qql.demo.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.hy.qql.demo.common.Constants;
import com.hy.qql.demo.config.AuthAccess;
import com.hy.qql.demo.entity.User;
import com.hy.qql.demo.exception.ServiceException;
import com.hy.qql.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserService userService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler){
        String token = request.getHeader("token");

        if(!(handler instanceof HandlerMethod)){
            return true;
        }else{
            HandlerMethod h = (HandlerMethod) handler;
            AuthAccess methodAnnotation = h.getMethodAnnotation(AuthAccess.class);
            if (methodAnnotation != null) {
                return true;
            }
        }

//        执行认证
        if(StrUtil.isBlank(token)){
            throw new ServiceException(Constants.CODE_401,"无token，请重新登录");
        }

//        获取token中的user  id
        String userId;
        try{
            userId = JWT.decode(token).getAudience().get(0);
        }catch(JWTDecodeException j){
            throw new ServiceException(Constants.CODE_401,"token验证失败");
        }
//        根据token中的userid查询数据库
        User user = userService.getById(userId);
        if(user == null){
            throw new ServiceException(Constants.CODE_401,"用户不存在");
        }

//        验证token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try{
//            验证token
            jwtVerifier.verify(token);
        }catch(JWTVerificationException e){
            throw new ServiceException(Constants.CODE_401,"token验证失败");
        }
        return true;
    }
}
