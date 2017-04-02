package com.releaseday.consumer.jwt.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.releaseday.api.config.ResultStatusCode;
import com.releaseday.api.entity.ResultMsg;

public class HTTPBasicAuthorizeAttribute implements Filter {

	private static String Name = "test";
	private static String Password = "test";

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		ResultStatusCode resultStatusCode = checkHTTPBasicAuthorize(request);
		if (resultStatusCode != ResultStatusCode.OK) {
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.setCharacterEncoding("UTF-8");
			httpResponse.setContentType("application/json; charset=utf-8");
			httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

			ObjectMapper mapper = new ObjectMapper();

			ResultMsg resultMsg = new ResultMsg(ResultStatusCode.PERMISSION_DENIED.getCode(), ResultStatusCode.PERMISSION_DENIED.getMsg(), null);
			httpResponse.getWriter().write(mapper.writeValueAsString(resultMsg));
			return;
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}

	private ResultStatusCode checkHTTPBasicAuthorize(ServletRequest request) {
		try {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			
			String auth = httpRequest.getHeader("Authorization");
			if ((auth != null) && (auth.length() > 6)) {
				String HeadStr = auth.substring(0, 5).toLowerCase();
				if (HeadStr.compareTo("basic") == 0) {
					auth = auth.substring(6, auth.length());
					String decodedAuth = getFromBASE64(auth);
					if (decodedAuth != null) {
						String[] UserArray = decodedAuth.split(":");
						System.out.println(UserArray[0]);
						System.out.println(UserArray[1]);
						if (UserArray != null && UserArray.length == 2) {
							if (UserArray[0].compareTo(Name) == 0 && UserArray[1].compareTo(Password) == 0) {
								return ResultStatusCode.OK;
							}
						}
					}
				}
			}
			return ResultStatusCode.PERMISSION_DENIED;
		} catch (Exception ex) {
			return ResultStatusCode.PERMISSION_DENIED;
		}

	}

	private String getFromBASE64(String s) {
		if (s == null)
			return null;
		try {
			return new String(Base64.decodeBase64(s.getBytes()));
		} catch (Exception e) {
			return null;
		}
	}

}
