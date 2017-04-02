package com.releaseday.consumer.jwt;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.releaseday.api.config.ResultStatusCode;
import com.releaseday.api.entity.ResultMsg;
import com.releaseday.api.entity.user.UserInfoCoEntity;
import com.releaseday.api.service.user.UserInfoService;
import com.releaseday.consumer.jwt.config.AudienceConf;
import com.releaseday.consumer.jwt.entity.AccessToken;
import com.releaseday.consumer.jwt.entity.LoginPara;
import com.releaseday.consumer.utils.CryptUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/auth")
@Api(value = "auth认证")
public class JsonWebToken {

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private AudienceConf audienceConf;

	@ApiOperation(value = "ResultMsg", notes = "用户登录后的返回信息")
	@ApiImplicitParam(name = "loginPara", value = "登录用户的用户信息及密码", required = true, dataType = "LoginPara")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Object getAccessToken(@RequestBody LoginPara loginPara, HttpServletResponse response, HttpServletRequest request) {
		ResultMsg resultMsg;
		try {
			if (loginPara.getClientId() == null || (loginPara.getClientId().compareTo(audienceConf.getClientId()) != 0)) {
				resultMsg = new ResultMsg(ResultStatusCode.INVALID_CLIENTID.getCode(),
						ResultStatusCode.INVALID_CLIENTID.getMsg(), null);
				return resultMsg;
			}
			//TODO 验证码校验在后面添加
 
			// 验证用户名密码
			UserInfoCoEntity userInfo = userInfoService.getUserByUsername(loginPara.getUsername());
			if (userInfo == null) {
				resultMsg = new ResultMsg(ResultStatusCode.INVALID_PASSWORD.getCode(),
						ResultStatusCode.INVALID_PASSWORD.getMsg(), null);
				return resultMsg;
			} else {
				String md5Password = CryptUtils.getMD5(loginPara.getPassword() + userInfo.getSalt());

				if (md5Password.compareTo(userInfo.getPassword()) != 0) {
					resultMsg = new ResultMsg(ResultStatusCode.INVALID_PASSWORD.getCode(),
							ResultStatusCode.INVALID_PASSWORD.getMsg(), null);
					return resultMsg;
				}
			}

			// 拼装accessToken
			String accessTokenString = JwtHelper.createJWT(loginPara.getUsername(), String.valueOf(userInfo.getUsername()),
					userInfo.getRole(), audienceConf.getClientId(), audienceConf.getName(), audienceConf.getExpiresSecond() * 1000,
					audienceConf.getBase64Secret());

			// 返回accessToken
			AccessToken accessTokenEntity = new AccessToken();
			accessTokenEntity.setAccess_token(accessTokenString);
			accessTokenEntity.setExpires_in((long) audienceConf.getExpiresSecond());
			accessTokenEntity.setToken_type("bearer");

			// 将token认证信息添加入cookie中
			Cookie accessTokenCookie = new Cookie("Authorization", "bearer " + accessTokenString);
			accessTokenCookie.setMaxAge(audienceConf.getExpiresSecond());// 设置为2天
			accessTokenCookie.setPath("/");
			// 将用户信息添加入cookie中
			Cookie usernameCookie = new Cookie("username", userInfo.getUsername());
			usernameCookie.setMaxAge(audienceConf.getExpiresSecond());
			usernameCookie.setPath("/");
			Cookie nameCookie = new Cookie("name", userInfo.getName());
			nameCookie.setMaxAge(audienceConf.getExpiresSecond());
			nameCookie.setPath("/");
			Cookie emailCookie = new Cookie("email", userInfo.getEmail());
			emailCookie.setMaxAge(audienceConf.getExpiresSecond());
			emailCookie.setPath("/");
			// 将cookie存入回应信息中
			response.addCookie(accessTokenCookie);
			response.addCookie(usernameCookie);
			response.addCookie(nameCookie);
			response.addCookie(emailCookie);
			
			resultMsg = new ResultMsg(ResultStatusCode.OK.getCode(), ResultStatusCode.OK.getMsg(), accessTokenEntity);
			return resultMsg;

		} catch (Exception ex) {
			resultMsg = new ResultMsg(ResultStatusCode.SYSTEM_ERR.getCode(), ResultStatusCode.SYSTEM_ERR.getMsg(),
					null);
			return resultMsg;
		}
	}
}