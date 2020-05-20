package com.example.interceptor;

import com.example.annotation.JwtIgnore;
import com.example.common.response.ResultCode;
import com.example.common.response.exception.DataException;
import com.example.domain.Account;
import com.example.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义token验证拦截器
 *
 * @author yichuan
 */
@Slf4j
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private Account account;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 忽略带JwtIgnore注解的请求, 不做后续token认证校验
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            JwtIgnore jwtIgnore = handlerMethod.getMethodAnnotation(JwtIgnore.class);
            if (jwtIgnore != null) {
                return true;
            }
        }
        if (HttpMethod.OPTIONS.equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        // 获取请求头信息authorization信息
        final String authHeader = request.getHeader(JwtTokenUtil.AUTH_HEADER_KEY);
        log.info("## authHeader= {}", authHeader);

        if (StringUtils.isBlank(authHeader) || !authHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
            log.info("### 用户未登录，请先登录 ###");
            throw new DataException(ResultCode.USER_NOT_LOGGED_IN);
        }
        // 获取token
        final String token = authHeader.substring(7);
        if (account == null) {
            //这里用到了IOC
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
            account = (Account) factory.getBean("account");
        }
        // 验证token是否有效--无效已做异常抛出，由全局异常处理后返回对应信息
        JwtTokenUtil.parseJWT(token, account.getBase64Secret());
        return true;
    }


}
