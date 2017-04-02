package com.releaseday.api.service.email;
/**
 * EMail推送类
 * 
 * @author hackless
 *
 */
public interface EmailPushService {
	
	/**
	 * 推送简单email信息
	 */
	public void sendRegisterEmail(String recipient, String title, String content);
	
}
