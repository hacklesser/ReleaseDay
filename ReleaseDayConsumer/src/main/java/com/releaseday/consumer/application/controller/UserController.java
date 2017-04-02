package com.releaseday.consumer.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.releaseday.api.config.ResultStatusCode;
import com.releaseday.api.entity.ResultMsg;
import com.releaseday.api.entity.user.UserInfoCoEntity;
import com.releaseday.api.service.appInfoPush.AppInfoPushService;
import com.releaseday.api.service.email.EmailPushService;
import com.releaseday.api.service.user.UserInfoService;
import com.releaseday.consumer.jwt.config.AudienceConf;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/manage/user")
@Api(value = "用户管理接口")
@EnableConfigurationProperties(AudienceConf.class)
public class UserController {

	@Autowired
	private AudienceConf audienceConf;

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private EmailPushService emailPushService;

	@Autowired
	private AppInfoPushService appInfoPushService;

	// @ApiOperation(value = "none", notes = "展示用户demo")
	// @ApiImplicitParam(name = "demo", value = "demo字符串", required = true,
	// dataType = "Map")
	// @RequestMapping(value = "/demo", method = RequestMethod.GET)
	// public Map index() {
	// Map resultMap = new HashMap();
	// resultMap.put("status", "success");
	// System.out.println("demo");
	// return resultMap;
	// }
	//
	// @ApiOperation(value = "moblie", notes = "根据用户手机号查询用户信息")
	// @ApiImplicitParam(name = "mobile", value = "mobile", required = true,
	// dataType = "Object")
	// @RequestMapping(value = "/mobile/{mobile:.+}", method =
	// RequestMethod.GET)
	// public Object getSingleLoanItem(@PathVariable("mobile") String mobile) {
	// Map resultMap = new HashMap();
	// resultMap.put("status", "success");
	// System.out.println("mobile");
	// return resultMap;
	// }

	@ApiOperation(value = "ResultMsg", notes = "获取Audience的返回信息")
	@RequestMapping(value = "/getAudience", method = RequestMethod.POST)
	public ResultMsg getAudience() {
		ResultMsg resultMsg = new ResultMsg(ResultStatusCode.OK.getCode(), ResultStatusCode.OK.getMsg(), audienceConf);
		return resultMsg;
	}

	@ApiOperation(value = "ResultMsg", notes = "获取单个用户信息的返回信息")
	@ApiImplicitParam(name = "id", value = "待查询用户的用户ID", required = true, dataType = "Long")
	@RequestMapping(value = "/queryUser/{id}", method = RequestMethod.POST)
	public ResultMsg queryUser(@PathVariable Long id) {
		UserInfoCoEntity userInfo = userInfoService.getUser(id);
		ResultMsg resultMsg = new ResultMsg(ResultStatusCode.OK.getCode(), ResultStatusCode.OK.getMsg(), userInfo);
		return resultMsg;
	}

	@ApiOperation(value = "ResultMsg", notes = "获取用户信息列表的返回信息")
	@ApiImplicitParam(name = "role", value = "待查询用户的用户分组", required = true, dataType = "String")
	@RequestMapping(value = "/queryUsers/{role}", method = RequestMethod.POST)
	public ResultMsg queryUsers(@PathVariable String role) {

		// emailPushService.sendSimpleEmail();

		// appInfoPushService.generalPush();

		List<UserInfoCoEntity> userInfoCoEntities = userInfoService.getUsers(role);
		ResultMsg resultMsg = new ResultMsg(ResultStatusCode.OK.getCode(), ResultStatusCode.OK.getMsg(), userInfoCoEntities);
		return resultMsg;
	}

	@ApiOperation(value = "ResultMsg", notes = "修改单个用户信息的返回信息")
	@ApiImplicitParam(name = "userInfo", value = "待修改用户的用户信息", required = true, dataType = "UserInfo")
	@RequestMapping(value = "/modifyUser", method = RequestMethod.POST)
	public ResultMsg modifyUser(@RequestBody UserInfoCoEntity userInfo) {
		return userInfoService.modifyUser(userInfo);
	}

	@ApiOperation(value = "ResultMsg", notes = "删除单个用户的返回信息")
	@ApiImplicitParam(name = "id", value = "待删除用户的用户ID", required = true, dataType = "Long")
	@RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.POST)
	public ResultMsg deleteUser(@PathVariable Long id) {
		return userInfoService.deleteUser(id);
	}

}
