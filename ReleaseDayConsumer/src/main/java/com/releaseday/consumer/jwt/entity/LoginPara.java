package com.releaseday.consumer.jwt.entity;

import java.io.Serializable;

/**
 * 认证信息类
 * 
 * @author hackless
 *
 */
public class LoginPara implements Serializable {

	private static final long serialVersionUID = -1860827749585767967L;

	private String clientId;
	private String username;
	private String password;
	private String captchaCode;
	private String captchaValue;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCaptchaCode() {
		return captchaCode;
	}

	public void setCaptchaCode(String captchaCode) {
		this.captchaCode = captchaCode;
	}

	public String getCaptchaValue() {
		return captchaValue;
	}

	public void setCaptchaValue(String captchaValue) {
		this.captchaValue = captchaValue;
	}

}
