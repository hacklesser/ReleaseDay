package com.releaseday.consumer.jwt.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.releaseday.api.config.ResultStatusCode;
import com.releaseday.api.entity.ResultMsg;
import com.releaseday.consumer.jwt.JwtHelper;
import com.releaseday.consumer.jwt.config.AudienceConf;

public class HTTPBearerAuthorizeAttribute implements Filter {
	@Autowired
	private AudienceConf audienceEntity;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		ResultMsg resultMsg;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		// 获取到登入时存入cookie中的token
		Cookie[] cookies = httpRequest.getCookies();
		String auth = null;
		try {
			for (Cookie cookie : cookies) {
				if ("Authorization".equals(cookie.getName())) {
					auth = cookie.getValue();
				}
			}
		} catch (Exception e) {
			auth = null;
		}
		// 验证token
		if ((auth != null) && (auth.length() > 7)) {
			String HeadStr = auth.substring(0, 6).toLowerCase();
			if (HeadStr.compareTo("bearer") == 0) {

				auth = auth.substring(7, auth.length());
				if (JwtHelper.parseJWT(auth, audienceEntity.getBase64Secret()) != null) {
					chain.doFilter(request, response);
					return;
				}
			}
		}

		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("application/json; charset=utf-8");
		httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		ObjectMapper mapper = new ObjectMapper();

		resultMsg = new ResultMsg(ResultStatusCode.INVALID_TOKEN.getCode(), ResultStatusCode.INVALID_TOKEN.getMsg(), null);
		httpResponse.getWriter().write(mapper.writeValueAsString(resultMsg));
		return;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}
}