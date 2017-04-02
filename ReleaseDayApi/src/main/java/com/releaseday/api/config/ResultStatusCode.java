package com.releaseday.api.config;

/**
 * 返回状态码枚举类
 * 
 * @author hackless
 *
 */
public enum ResultStatusCode {

	OK(0, "OK"), BLANK_ERR(-1, "Null"), 
	SYSTEM_ERR(30001, "System Error!"), 
	NORMAL_ERR(30002, "Normal Error!"), 
	INVALID_CLIENTID(30003, "Invalid Clientid!"),  
	INVALID_PASSWORD(30004, "Username or Password is Incorrect!"),  
	INVALID_CAPTCHA(30005, "Invalid Captcha or Captcha Overdue!"),  
	INVALID_TOKEN(30006, "Invalid Token!"),
	PERMISSION_DENIED(30007, "Permission Denied!");

	private int code;
	private String Msg;

	private ResultStatusCode(int code, String msg) {
		this.code = code;
		Msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}



	public String getMsg() {
		return Msg;
	}

	public void setMsg(String msg) {
		Msg = msg;
	}

}
