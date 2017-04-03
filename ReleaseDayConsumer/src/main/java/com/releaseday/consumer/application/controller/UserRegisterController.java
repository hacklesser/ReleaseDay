package com.releaseday.consumer.application.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.releaseday.api.config.ResultStatusCode;
import com.releaseday.api.entity.ResultMsg;
import com.releaseday.api.entity.user.UserInfoCoEntity;
import com.releaseday.api.service.email.EmailPushService;
import com.releaseday.api.service.user.UserInfoService;
import com.releaseday.consumer.utils.CryptUtils;
import com.releaseday.consumer.utils.StringRandomUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/register/user")
@Api(value = "用户注册接口")
public class UserRegisterController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private EmailPushService emailPushService;

	@ApiOperation(value = "ResultMsg", notes = "增加单个用户信息的返回信息")
	@ApiImplicitParam(name = "userInfo", value = "待增加用户的用户信息", required = true, dataType = "UserInfo")
	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public Object registerUser(@RequestBody UserInfoCoEntity userInfoCoEntity) {

		userInfoCoEntity.setRegisterTime(new Date());
		userInfoCoEntity.setStatus(0);
		userInfoCoEntity.setValidateCode(CryptUtils.getMD5(StringRandomUtils.generateRandomValidateCode()));

		userInfoCoEntity.setSalt(StringRandomUtils.generateRandomSalt());
		userInfoCoEntity.setPassword(CryptUtils.getMD5(userInfoCoEntity.getPassword() + userInfoCoEntity.getSalt()));
		UserInfoCoEntity returnUserInfo = userInfoService.registerUser(userInfoCoEntity);
		ResultMsg resultMsg = null;
		// TODO 将需要隐藏不让用户查看的值抹除

		
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// 发送验证邮件
				// 邮件内容
				StringBuffer content = new StringBuffer("点击下面链接激活账号，48小时生效，否则重新注册账号，链接只能使用一次，请尽快激活！\n");
				content.append("http://localhost:8070/sbprdc/register/user/validateUser?email=");
				content.append(userInfoCoEntity.getEmail());
				content.append("&validateCode=");
				content.append(userInfoCoEntity.getValidateCode());
				
				emailPushService.sendRegisterEmail(userInfoCoEntity.getEmail(), "测试网站验证激活测试邮件", content.toString());
			}
		});
		thread.start();
		
		if (returnUserInfo != null) {
			resultMsg = new ResultMsg(ResultStatusCode.OK.getCode(), ResultStatusCode.OK.getMsg(), "账号注册成功，请在期限内验证邮箱！");
			logger.info("用户:"+userInfoCoEntity.getUsername()+":注册成功...");
		} else {
			resultMsg = new ResultMsg(ResultStatusCode.SYSTEM_ERR.getCode(), ResultStatusCode.SYSTEM_ERR.getMsg(), "账号注册失败！");
			logger.info("用户:"+userInfoCoEntity.getUsername()+":注册失败...");
		}
//		resultMsg = new ResultMsg(ResultStatusCode.OK.getCode(), ResultStatusCode.OK.getMsg(), "账号注册成功，请在期限内验证邮箱！");
//		logger.info("用户:"+userInfoCoEntity.getUsername()+":注册成功...");

		return resultMsg;
	}

	@ApiOperation(value = "ResultMsg", notes = "邮箱验证结果的返回信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "email", value = "待验证用户的用户邮箱", required = true, dataType = "String"),
			@ApiImplicitParam(name = "validateCode", value = "待验证用户的用户验证码", required = true, dataType = "String") })
	@RequestMapping(value = "/validateUser", method = RequestMethod.GET)
	public Object validateUser(@RequestParam String email, @RequestParam String validateCode) {
		Date currentDate = new Date();
		UserInfoCoEntity userInfo = userInfoService.getUserByEmail(email);
		System.out.println(userInfo.getUsername());
		ResultMsg resultMsg = null;
		if (userInfo != null && userInfo.getStatus() != -1 && currentDate.after(userInfo.getRegisterTime()) && validateCode.equals(userInfo.getValidateCode()) && userInfo.getValidateCode() != "validated") {
			userInfo.setStatus(1);
			userInfo.setValidateCode("validated");
			userInfoService.modifyUser(userInfo);
			resultMsg = new ResultMsg(ResultStatusCode.OK.getCode(), ResultStatusCode.OK.getMsg(), "邮箱验证成功");
			logger.info("用户:"+userInfo.getUsername()+":邮箱:"+userInfo.getEmail()+":验证成功...");
		} else {
			resultMsg = new ResultMsg(ResultStatusCode.SYSTEM_ERR.getCode(), ResultStatusCode.SYSTEM_ERR.getMsg(), "邮箱验证失败");
			logger.info("用户:"+userInfo.getUsername()+":邮箱:"+userInfo.getEmail()+":验证失败...");
		}
		
		return resultMsg;
	}
}
