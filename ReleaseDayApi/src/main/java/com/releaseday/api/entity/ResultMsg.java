package com.releaseday.api.entity;

import java.io.Serializable;

/**
 * 返回信息实体类
 * 
 * @author hackless
 *
 */
public class ResultMsg implements Serializable{

	private static final long serialVersionUID = 8241182990662198788L;
	
	private int code;
	private String message;
	private Object responseBody;

	public ResultMsg(int code, String message, Object responseBody) {
		this.code = code;
		this.message = message;
		this.responseBody = responseBody;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(Object responseBody) {
		this.responseBody = responseBody;
	}

}
