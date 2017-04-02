package com.releaseday.provider.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.releaseday.api.service.email.EmailPushService;

@Service(value = "emailPushService")
public class EmailPushServiceImpl implements EmailPushService{

	@Autowired
	private JavaMailSender javaMailSender;

	/**
	 * 修改application.properties的用户，才能发送。
	 */
	@Override
	public void sendRegisterEmail(String recipient, String title, String content) {
		
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom("hackless@qq.com");// 发送者
		message.setTo(recipient);// 接收者
		message.setSubject(title);// 邮件主题
		message.setText(content);// 邮件内容
		
		javaMailSender.send(message);// 发送邮件
	}
	
}
