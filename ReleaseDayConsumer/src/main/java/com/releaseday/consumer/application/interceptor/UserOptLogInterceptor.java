package com.releaseday.consumer.application.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.releaseday.consumer.utils.IpAddressUtils;

public class UserOptLogInterceptor implements HandlerInterceptor{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		String methodName = handlerMethod.getMethod().getName();
		// 获取客户端IP地址信息
		String clientIpAddress = IpAddressUtils.getClientIpAddress(request);
		try {
			Cookie[] cookies = request.getCookies();
			String username = null;
			for (Cookie cookie : cookies) {
				if ("username".equals(cookie.getName())) {
					username = cookie.getValue();
				}
			}
			logger.info("用户:"+username+":"+clientIpAddress+":进行了:"+methodName+"操作...");
		} catch (Exception e) {
			logger.info("未知用户IP:"+clientIpAddress+":进行了"+methodName+"操作...");
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}


}
